package com.Hospital_Management_MiniProject_2.repository;

import com.Hospital_Management_MiniProject_2.entity.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    private Doctor doctor;

    @BeforeEach
    void setUp() {
        doctor = new Doctor();
        doctor.setId("d1");
        doctor.setName("Dr. John Doe");
        // Set other fields as necessary
    }

    @Test
    void testSaveDoctor() {
        Doctor savedDoctor = doctorRepository.save(doctor);
        assertNotNull(savedDoctor);
        assertEquals("d1", savedDoctor.getId());
        assertEquals("Dr. John Doe", savedDoctor.getName());
    }

    @Test
    void testFindById() {
        doctorRepository.save(doctor);
        Optional<Doctor> found = doctorRepository.findById("d1");
        assertTrue(found.isPresent());
        assertEquals("Dr. John Doe", found.get().getName());
    }

    @Test
    void testFindAll() {
        doctorRepository.save(doctor);
        List<Doctor> doctors = doctorRepository.findAll();
        assertFalse(doctors.isEmpty());
        assertEquals(3, doctors.size());
    }

    @Test
    void testUpdateDoctor() {
        doctorRepository.save(doctor);
        doctor.setName("Dr. Jane Smith");
        Doctor updatedDoctor = doctorRepository.save(doctor);
        assertEquals("Dr. Jane Smith", updatedDoctor.getName());
    }

    @Test
    void testDeleteDoctor() {
        doctorRepository.save(doctor);
        doctorRepository.deleteById("d1");
        Optional<Doctor> found = doctorRepository.findById("d1");
        assertFalse(found.isPresent());
    }
}
