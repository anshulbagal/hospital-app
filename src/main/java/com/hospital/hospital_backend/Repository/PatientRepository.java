package com.hospital.hospital_backend.Repository;

import com.hospital.hospital_backend.Entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Integer> {

    // Find patient by bed number
    Optional<PatientEntity> findByBedNumber(int bedNumber);

    // Delete patient by bed number
    void deleteByBedNumber(int bedNumber);

    // Check if bed is occupied
    boolean existsByBedNumber(int bedNumber);
}
