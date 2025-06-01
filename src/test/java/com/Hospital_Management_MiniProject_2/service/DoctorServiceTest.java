package com.Hospital_Management_MiniProject_2.service;

import com.Hospital_Management_MiniProject_2.entity.Doctor;
import com.Hospital_Management_MiniProject_2.exception.ResourceNotFoundException;
import com.Hospital_Management_MiniProject_2.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    private Doctor doctor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        doctor = new Doctor();
        doctor.setId("d1");
        doctor.setName("Dr. John Doe");
        // set other fields if needed
    }

    @Test
    void testCreateDoctor() {
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        Doctor created = doctorService.createDoctor(doctor);
        assertNotNull(created);
        assertEquals("d1", created.getId());
        verify(doctorRepository).save(doctor);
    }

    @Test
    void testGetAllDoctors() {
        List<Doctor> doctors = List.of(doctor);
        when(doctorRepository.findAll()).thenReturn(doctors);

        List<Doctor> result = doctorService.getAllDoctors();
        assertEquals(1, result.size());
        verify(doctorRepository).findAll();
    }

    @Test
    void testGetDoctorById_Found() {
        when(doctorRepository.findById("d1")).thenReturn(Optional.of(doctor));

        Doctor found = doctorService.getDoctorById("d1");
        assertNotNull(found);
        assertEquals("d1", found.getId());
    }

    @Test
    void testGetDoctorById_NotFound() {
        when(doctorRepository.findById("d2")).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            doctorService.getDoctorById("d2");
        });

        assertTrue(ex.getMessage().contains("Doctor not found with id: d2"));
    }

    @Test
    void testUpdateDoctor() {
        Doctor updatedDoctor = new Doctor();
        updatedDoctor.setName("Dr. Jane Smith");

        when(doctorRepository.findById("d1")).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(any(Doctor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Doctor result = doctorService.updateDoctor("d1", updatedDoctor);
        assertEquals("d1", result.getId());
        assertEquals("Dr. Jane Smith", result.getName());

        verify(doctorRepository).findById("d1");
        verify(doctorRepository).save(updatedDoctor);
    }

    @Test
    void testDeleteDoctor() {
        when(doctorRepository.findById("d1")).thenReturn(Optional.of(doctor));

        doctorService.deleteDoctor("d1");

        verify(doctorRepository).deleteById("d1");
    }

    @Test
    void testDeleteDoctor_NotFound() {
        when(doctorRepository.findById("d2")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            doctorService.deleteDoctor("d2");
        });
        verify(doctorRepository, never()).deleteById(anyString());
    }
}
