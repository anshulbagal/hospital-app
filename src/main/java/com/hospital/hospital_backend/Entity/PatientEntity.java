package com.hospital.hospital_backend.Entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "patients")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "bed_number", nullable = false)
    private int bedNumber;

    @Column(nullable = false)
    private String name;

    private String age;
    private String gender;
    private String diagnosis;

    @Column(name = "ward_type", nullable = false)
    private String wardType;

    @Column(name = "admitted_at")
    private LocalDateTime admittedAt;

    // ── Getters & Setters ─────────────────────
    public int getId()                        { return id; }
    public void setId(int id)                 { this.id = id; }

    public int getBedNumber()                 { return bedNumber; }
    public void setBedNumber(int bedNumber)   { this.bedNumber = bedNumber; }

    public String getName()                   { return name; }
    public void setName(String name)          { this.name = name; }

    public String getAge()                    { return age; }
    public void setAge(String age)            { this.age = age; }

    public String getGender()                 { return gender; }
    public void setGender(String gender)      { this.gender = gender; }

    public String getDiagnosis()              { return diagnosis; }
    public void setDiagnosis(String diagnosis){ this.diagnosis = diagnosis; }

    public String getWardType()               { return wardType; }
    public void setWardType(String wardType)  { this.wardType = wardType; }

    public LocalDateTime getAdmittedAt()              { return admittedAt; }
    public void setAdmittedAt(LocalDateTime admittedAt){ this.admittedAt = admittedAt; }
}
