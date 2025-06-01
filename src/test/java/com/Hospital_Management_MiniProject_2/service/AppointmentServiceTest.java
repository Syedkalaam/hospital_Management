package com.Hospital_Management_MiniProject_2.service;

import com.Hospital_Management_MiniProject_2.entity.Appointment;
import com.Hospital_Management_MiniProject_2.entity.Doctor;
import com.Hospital_Management_MiniProject_2.entity.Patient;
import com.Hospital_Management_MiniProject_2.exception.ResourceNotFoundException;
import com.Hospital_Management_MiniProject_2.repository.AppointmentRepository;
import com.Hospital_Management_MiniProject_2.repository.DoctorRepository;
import com.Hospital_Management_MiniProject_2.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBookAppointment_success() {
        String doctorId = "doc1";
        String patientId = "pat1";

        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        doctor.setName("Dr. Smith");
        doctor.setAppointments(new ArrayList<>());
        doctor.setPatients(new ArrayList<>());

        Patient patient = new Patient();
        patient.setId(patientId);
        patient.setName("John Doe");
        patient.setAppointments(new ArrayList<>());

        Appointment appointment = new Appointment();
        appointment.setPatientId(patientId);

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        when(appointmentRepository.save(any(Appointment.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(doctorRepository.save(any(Doctor.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        appointmentService.bookAppointment(doctorId, appointment);

        assertEquals(doctorId, appointment.getDoctorId());
        assertEquals("Dr. Smith", appointment.getDoctorName());
        assertEquals("John Doe", appointment.getPatientName());

        verify(appointmentRepository).save(appointment);
        verify(doctorRepository).save(doctor);
        verify(patientRepository).save(patient);
        assertTrue(doctor.getAppointments().contains(appointment));
        assertTrue(doctor.getPatients().contains(patient));
        assertTrue(patient.getAppointments().contains(appointment));
    }

    @Test
    void testBookAppointment_doctorNotFound() {
        when(doctorRepository.findById("doc1")).thenReturn(Optional.empty());
        Appointment appointment = new Appointment();
        appointment.setPatientId("pat1");

        assertThrows(ResourceNotFoundException.class, () -> appointmentService.bookAppointment("doc1", appointment));
    }

    @Test
    void testBookAppointment_patientNotFound() {
        String doctorId = "doc1";
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(patientRepository.findById("pat1")).thenReturn(Optional.empty());

        Appointment appointment = new Appointment();
        appointment.setPatientId("pat1");

        assertThrows(ResourceNotFoundException.class, () -> appointmentService.bookAppointment(doctorId, appointment));
    }

    @Test
    void testGetAllAppointments() {
        List<Appointment> appointmentList = List.of(new Appointment(), new Appointment());
        when(appointmentRepository.findAll()).thenReturn(appointmentList);

        List<Appointment> result = appointmentService.getAllAppointments();
        assertEquals(2, result.size());
    }

    @Test
    void testGetAppointmentById_found() {
        Appointment appointment = new Appointment();
        appointment.setAppointmentIds("app1");

        when(appointmentRepository.findById("app1")).thenReturn(Optional.of(appointment));

        Appointment found = appointmentService.getAppointmentById("app1");
        assertEquals("app1", found.getAppointmentIds());
    }

    @Test
    void testGetAppointmentById_notFound() {
        when(appointmentRepository.findById("app1")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> appointmentService.getAppointmentById("app1"));
    }

    @Test
    void testUpdateAppointment() {
        Appointment existingAppointment = new Appointment();
        existingAppointment.setAppointmentIds("app1");

        Appointment updatedAppointment = new Appointment();
        updatedAppointment.setAppointmentIds("app1");

        when(appointmentRepository.findById("app1")).thenReturn(Optional.of(existingAppointment));
        when(appointmentRepository.save(updatedAppointment)).thenReturn(updatedAppointment);

        Appointment result = appointmentService.updateAppointment("app1", updatedAppointment);
        assertEquals("app1", result.getAppointmentIds());
        verify(appointmentRepository).save(updatedAppointment);
    }

    @Test
    void testDeleteAppointment_success() {
        String doctorId = "doc1";
        String appointmentIds = "app1";
        String patientId = "pat1";

        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        doctor.setAppointments(new ArrayList<>());

        Appointment appointment = new Appointment();
        appointment.setAppointmentIds(appointmentIds);
        appointment.setPatientId(patientId);

        Patient patient = new Patient();
        patient.setId(patientId);
        patient.setAppointments(new ArrayList<>());

        // Add appointment to doctor and patient lists so it can be removed
        doctor.getAppointments().add(appointment);
        patient.getAppointments().add(appointment);

        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(appointmentRepository.findById(appointmentIds)).thenReturn(Optional.of(appointment));
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));

        appointmentService.deleteAppointment(doctorId, appointmentIds);

        verify(patientRepository).save(patient);
        verify(doctorRepository).save(doctor);
        verify(appointmentRepository).deleteById(appointmentIds);

        assertFalse(patient.getAppointments().contains(appointment));
        assertFalse(doctor.getAppointments().contains(appointment));
    }

    @Test
    void testDeleteAppointment_notFound() {
        when(doctorRepository.findById("doc1")).thenReturn(Optional.empty());
        when(appointmentRepository.findById("app1")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> appointmentService.deleteAppointment("doc1", "app1"));
    }
}
