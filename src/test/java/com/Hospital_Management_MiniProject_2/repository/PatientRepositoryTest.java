package com.Hospital_Management_MiniProject_2.repository;


import com.Hospital_Management_MiniProject_2.entity.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PatientRepositoryTest {

    @Autowired
    private PatientRepository patientRepository;

    @Test
    void addPatient(){
        Patient patient=new Patient();
        patient.setName("arunesh");
        patient.setAge(23);
        patient.setGender("male");
        patient.setContactNumber("7094950916");
        patient.setAddress("chennai");
        Patient savedpatient =patientRepository.save(patient);
        assertThat(savedpatient).isNotNull();
    }

    @Test
    void findByid(){
        Patient patient=new Patient();
        patient.setName("arunesh");
        patient.setAge(23);
        patient.setGender("male");
        patient.setContactNumber("7094950916");
        patient.setAddress("chennai");
        Patient savedpatient =patientRepository.save(patient);
        Optional<Patient> found = patientRepository.findById(savedpatient.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("arunesh");
    }

    @Test
    void findAll(){
        List<Patient> patientList = patientRepository.findAll();
        assertEquals(5,patientList.size());
    }

    @Test
    void  deleteByid(){
        Patient patient=new Patient();
        patient.setName("arunesh");
        patient.setAge(23);
        patient.setGender("male");
        patient.setContactNumber("7094950916");
        patient.setAddress("chennai");
        Patient savedpatient =patientRepository.save(patient);
        patientRepository.deleteById(savedpatient.getId());
        Optional<Patient> deletedId=patientRepository.findById(savedpatient.getId());
        assertThat(deletedId).isEmpty();
    }

}
