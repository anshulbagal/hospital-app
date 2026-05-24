package com.hospital.hospital_backend.Service;

import com.hospital.hospital_backend.Entity.ActivityLogEntity;
import com.hospital.hospital_backend.Entity.PatientEntity;
import com.hospital.hospital_backend.Repository.ActivityLogRepository;
import com.hospital.hospital_backend.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class HospitalService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ActivityLogRepository activityLogRepository;

    private static final int TOTAL_BEDS = 6;
    private static final int ICU_RATE   = 1000;
    private static final int GEN_RATE   = 500;

    // ── GET all patients ──────────────────────────────────────────
    public List<PatientEntity> getAllPatients() {
        return patientRepository.findAll();
    }

    // ── ADMIT patient ─────────────────────────────────────────────
    public Map<String, Object> admitPatient(PatientEntity patient) {
        Map<String, Object> response = new HashMap<>();

        // Find first free bed
        int freeBed = -1;
        for (int i = 1; i <= TOTAL_BEDS; i++) {
            if (!patientRepository.existsByBedNumber(i)) {
                freeBed = i;
                break;
            }
        }

        if (freeBed == -1) {
            response.put("success", false);
            response.put("message", "All beds are currently occupied.");
            return response;
        }

        // Save patient
        patient.setBedNumber(freeBed);
        patient.setAdmittedAt(LocalDateTime.now());
        patientRepository.save(patient);

        // Save log
        ActivityLogEntity log = new ActivityLogEntity();
        log.setLogType("ADMIT");
        log.setMessage(patient.getName() + " admitted to Bed " + freeBed
                + " (" + patient.getWardType() + ")");
        log.setFee(0);
        log.setCreatedAt(LocalDateTime.now());
        activityLogRepository.save(log);

        response.put("success", true);
        response.put("message", log.getMessage());
        response.put("bedNumber", freeBed);
        return response;
    }

    // ── DISCHARGE patient ─────────────────────────────────────────
    @Transactional
    public Map<String, Object> dischargePatient(int bedNumber, int hours) {
        Map<String, Object> response = new HashMap<>();

        Optional<PatientEntity> optional = patientRepository.findByBedNumber(bedNumber);

        if (optional.isEmpty()) {
            response.put("success", false);
            response.put("message", "Bed " + bedNumber + " is empty.");
            return response;
        }

        PatientEntity patient = optional.get();
        int rate = patient.getWardType().equals("ICU") ? ICU_RATE : GEN_RATE;
        int fee  = rate * hours;

        // Save log
        ActivityLogEntity log = new ActivityLogEntity();
        log.setLogType("DISCHARGE");
        log.setMessage(patient.getName() + " discharged from Bed " + bedNumber
                + " (" + patient.getWardType() + ") after " + hours + " hrs");
        log.setFee(fee);
        log.setCreatedAt(LocalDateTime.now());
        activityLogRepository.save(log);

        // Delete patient
        patientRepository.deleteByBedNumber(bedNumber);

        response.put("success", true);
        response.put("message", log.getMessage());
        response.put("fee", fee);
        return response;
    }

    // ── GET all logs ──────────────────────────────────────────────
    public List<ActivityLogEntity> getAllLogs() {
        return activityLogRepository.findAllByOrderByCreatedAtAsc();
    }

    // ── STATS ─────────────────────────────────────────────────────
    public Map<String, Object> getStats() {
        List<PatientEntity> all = patientRepository.findAll();
        int occupied = all.size();
        int icuCount = (int) all.stream()
                .filter(p -> p.getWardType().equals("ICU")).count();
        int genCount = occupied - icuCount;

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalBeds", TOTAL_BEDS);
        stats.put("occupied",  occupied);
        stats.put("available", TOTAL_BEDS - occupied);
        stats.put("icuCount",  icuCount);
        stats.put("genCount",  genCount);
        return stats;
    }
}
