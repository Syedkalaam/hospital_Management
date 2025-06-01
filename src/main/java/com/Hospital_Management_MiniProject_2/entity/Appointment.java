package com.Hospital_Management_MiniProject_2.entity;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "appointments")
public class Appointment {


    @Id
    private String appointmentIds;

    @NotBlank(message = "Doctor ID is required")
    private String doctorId;

    @NotNull(message = "Appointment date and time is required")
    private String dateandTime;

    private String patientId;

    private String patientName;

    private String doctorName;

    public Appointment() {
    }

    public Appointment(String appointmentIds, String doctorId, String dateandTime, String patientId, String patientName, String doctorName) {
        this.appointmentIds = appointmentIds;
        this.doctorId = doctorId;
        this.dateandTime = dateandTime;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorName = doctorName;
    }

    public String getAppointmentIds() {
        return appointmentIds;
    }

    public void setAppointmentIds(String appointmentIds) {
        this.appointmentIds = appointmentIds;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDateandTime() {
        return dateandTime;
    }

    public void setDateandTime(String dateandTime) {
        this.dateandTime = dateandTime;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
}
