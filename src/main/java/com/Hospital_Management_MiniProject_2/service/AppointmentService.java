package com.Hospital_Management_MiniProject_2.service;

import com.Hospital_Management_MiniProject_2.entity.Appointment;
import com.Hospital_Management_MiniProject_2.entity.Doctor;
import com.Hospital_Management_MiniProject_2.entity.Patient;
import com.Hospital_Management_MiniProject_2.exception.ResourceNotFoundException;
import com.Hospital_Management_MiniProject_2.repository.AppointmentRepository;
import com.Hospital_Management_MiniProject_2.repository.DoctorRepository;
import com.Hospital_Management_MiniProject_2.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;



    public void bookAppointment(String doctorId , Appointment appointment){
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(()->{
            throw new ResourceNotFoundException("Doctor not found");
        });

        String patientId = appointment.getPatientId();
        Patient patient = patientRepository.findById(patientId).
                orElseThrow(()->{
                    throw new ResourceNotFoundException("Patient not found");
                });

        appointment.setDoctorId(doctorId);
        appointment.setDoctorName(doctor.getName());
        appointment.setPatientName(patient.getName());
        appointment = appointmentRepository.save(appointment);
        doctor.getAppointments().add(appointment);
        doctor.getPatients().add(patient);
        patient.getAppointments().add(appointment);
        doctorRepository.save(doctor);
        patientRepository.save(patient);
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(String id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with id: " + id));
    }





    public Appointment updateAppointment(String id, Appointment updatedAppointment) {
        getAppointmentById(id);
        updatedAppointment.setAppointmentIds(id);
        return appointmentRepository.save(updatedAppointment);
    }

    public void deleteAppointment(String doctorId, String appointmentIds) {

        Optional<Doctor> optionalDoctor = doctorRepository.findById(doctorId);
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentIds);

        if (optionalDoctor.isPresent() && optionalAppointment.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            Appointment appointment = optionalAppointment.get();

            String patientId=appointment.getPatientId();
            Patient existpatient=patientRepository.findById(patientId).orElseThrow(()->{throw new ResourceNotFoundException("patient not found");});
            existpatient.getAppointments().removeIf(appo->appo.getAppointmentIds().equals(appointmentIds));
            patientRepository.save(existpatient);

            doctor.getAppointments().removeIf(appo -> appo.getAppointmentIds().equals(appointmentIds));
            doctor.getPatients().removeIf(patient -> patient.getId().equals(appointment.getPatientId()));
            doctorRepository.save(doctor);

            // Delete the medicine document
            appointmentRepository.deleteById(appointmentIds);
        } else {
            throw new IllegalArgumentException("Doctor or Appointment not found");
        }
    }
}
