package com.Hospital_Management_MiniProject_2.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "medicines")
public class Medicine {

    @Id
    private String id;

    private String name;
    private String dosage;
    private String description;
    private String expiryDate;

    private String doctorId;
    private String patientId;

    public Medicine() {
    }

    public Medicine(String id, String name, String dosage, String description, String expiryDate, String doctorId, String patientId) {
        this.id = id;
        this.name = name;
        this.dosage = dosage;
        this.description = description;
        this.expiryDate = expiryDate;
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
