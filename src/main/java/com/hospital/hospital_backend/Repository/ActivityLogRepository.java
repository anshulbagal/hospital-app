package com.hospital.hospital_backend.Repository;

import com.hospital.hospital_backend.Entity.ActivityLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ActivityLogRepository extends JpaRepository<ActivityLogEntity, Integer> {

    // Get all logs sorted by time
    List<ActivityLogEntity> findAllByOrderByCreatedAtAsc();
}
