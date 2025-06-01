package com.Hospital_Management_MiniProject_2.repository;

import com.Hospital_Management_MiniProject_2.entity.Appointment;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    private static String appointmentId;

    @Test
    @Order(1)
    void testCreateAppointment() {
        Appointment appointment = new Appointment();
        appointment.setDoctorId("doc123");
        appointment.setPatientId("pat123");
        appointment.setDoctorName("Dr. Strange");
        appointment.setPatientName("Peter Parker");

        Appointment saved = appointmentRepository.save(appointment);
        appointmentId = saved.getAppointmentIds();

        assertNotNull(saved);
        assertNotNull(saved.getAppointmentIds());
        assertEquals("doc123", saved.getDoctorId());
    }

    @Test
    @Order(2)
    void testFindAppointmentById() {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        assertTrue(optionalAppointment.isPresent());

        Appointment appointment = optionalAppointment.get();
        assertEquals(appointmentId, appointment.getAppointmentIds());
    }

    @Test
    @Order(3)
    void testFindAllAppointments() {
        List<Appointment> appointments = appointmentRepository.findAll();
        assertFalse(appointments.isEmpty());
    }

    @Test
    @Order(4)
    void testUpdateAppointment() {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        assertTrue(optionalAppointment.isPresent());

        Appointment appointment = optionalAppointment.get();
        appointment.setDoctorName("Dr. Who");

        Appointment updated = appointmentRepository.save(appointment);
        assertEquals("Dr. Who", updated.getDoctorName());
    }

    @Test
    @Order(5)
    void testDeleteAppointment() {
        appointmentRepository.deleteById(appointmentId);

        Optional<Appointment> optionalAppointment = appointmentRepository.findById(appointmentId);
        assertFalse(optionalAppointment.isPresent());
    }
}
