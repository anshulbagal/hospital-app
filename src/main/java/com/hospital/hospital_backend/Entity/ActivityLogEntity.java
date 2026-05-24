package com.hospital.hospital_backend.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "activity_log")
public class ActivityLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "log_type", nullable = false)
    private String logType;   // "ADMIT" or "DISCHARGE"

    @Column(nullable = false)
    private String message;

    private int fee;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // ── Getters & Setters ─────────────────────
    public int getId()                          { return id; }
    public void setId(int id)                   { this.id = id; }

    public String getLogType()                  { return logType; }
    public void setLogType(String logType)      { this.logType = logType; }

    public String getMessage()                  { return message; }
    public void setMessage(String message)      { this.message = message; }

    public int getFee()                         { return fee; }
    public void setFee(int fee)                 { this.fee = fee; }

    public LocalDateTime getCreatedAt()                 { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt)   { this.createdAt = createdAt; }
}
