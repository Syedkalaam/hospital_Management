package com.Hospital_Management_MiniProject_2.service;

import com.Hospital_Management_MiniProject_2.entity.Appointment;
import com.Hospital_Management_MiniProject_2.entity.Doctor;
import com.Hospital_Management_MiniProject_2.entity.Medicine;
import com.Hospital_Management_MiniProject_2.entity.Patient;
import com.Hospital_Management_MiniProject_2.exception.ResourceNotFoundException;
import com.Hospital_Management_MiniProject_2.repository.AppointmentRepository;
import com.Hospital_Management_MiniProject_2.repository.DoctorRepository;
import com.Hospital_Management_MiniProject_2.repository.MedicineRepository;
import com.Hospital_Management_MiniProject_2.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Patient createPatient(Patient patient) {
        return patientRepository.save(patient);
    }



    public List<Patient> getAllPatients() {
        return patientRepository.findAll();

    }

    public Patient getPatientById(String id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient not found with id: " + id));
    }

    public Patient updatePatient(String id, Patient updatedPatient) {
        getPatientById(id);
        updatedPatient.setId(id);
        return patientRepository.save(updatedPatient);
    }

    public void deletePatient(String patientId) {
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();


            // Delete all referenced medicines
            for (Medicine med : patient.getMedicines()) {
                Doctor doctor = doctorRepository.findById(med.getDoctorId())
                        .orElseThrow(()->{
                            throw new ResourceNotFoundException("Doctor not found");
                        });
                doctor.getPatients().removeIf(pat->pat.getId().equals(med.getPatientId()));
                doctor.getMedicines().removeIf(medicine->medicine.getId().equals(med.getId()));
                doctorRepository.save(doctor);
                medicineRepository.deleteById(med.getId());
            }
            for (Appointment appo:patient.getAppointments()){
                appointmentRepository.deleteById(appo.getAppointmentIds());
            }

            // Now delete the patient
            patientRepository.deleteById(patientId);
        } else {
            throw new IllegalArgumentException("Patient not found with ID: " + patientId);
        }
    }

}
