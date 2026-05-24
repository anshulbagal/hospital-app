package com.hospital.hospital_backend.Controller;

import com.hospital.hospital_backend.Entity.ActivityLogEntity;
import com.hospital.hospital_backend.Entity.PatientEntity;
import com.hospital.hospital_backend.Service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/hospital")
@CrossOrigin(origins = "*")  // allows JavaFX to call this API
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    // GET  /api/hospital/patients  → get all admitted patients
    @GetMapping("/patients")
    public List<PatientEntity> getAllPatients() {
        return hospitalService.getAllPatients();
    }

    // POST /api/hospital/admit  → admit a new patient
    @PostMapping("/admit")
    public Map<String, Object> admitPatient(@RequestBody PatientEntity patient) {
        return hospitalService.admitPatient(patient);
    }

    // POST /api/hospital/discharge/{bedNumber}?hours=5  → discharge patient
    @PostMapping("/discharge/{bedNumber}")
    public Map<String, Object> dischargePatient(
            @PathVariable int bedNumber,
            @RequestParam int hours) {
        return hospitalService.dischargePatient(bedNumber, hours);
    }

    // GET  /api/hospital/logs  → get full activity history
    @GetMapping("/logs")
    public List<ActivityLogEntity> getAllLogs() {
        return hospitalService.getAllLogs();
    }

    // GET  /api/hospital/stats  → get bed stats
    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        return hospitalService.getStats();
    }
}