package com.Hospital_Management_MiniProject_2.service;

import com.Hospital_Management_MiniProject_2.entity.Patient;
import com.Hospital_Management_MiniProject_2.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private  PatientService patientService;

    private Patient patient1;
    private Patient patient2;


    @BeforeEach
    void setup(){
        patient1=new Patient();
        patient1.setId("1l");
        patient1.setName("arunesh");
        patient1.setAge(23);
        patient1.setGender("male");
        patient1.setContactNumber("7094950916");
        patient1.setAddress("chennai");


        patient2=new Patient();
        patient2.setId("2l");
        patient2.setName("arjun");
        patient2.setAge(23);
        patient2.setGender("male");
        patient2.setContactNumber("7794950916");
        patient2.setAddress("salem");
    }

    @Test
    void getAllPatients(){
        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient1,patient2));
        List<Patient> patients=patientService.getAllPatients();

        assertEquals(2,patients.size());
        verify(patientRepository, times(1)).findAll();
    }

    @Test
    void getAllPatients_emptylist(){
        when(patientRepository.findAll()).thenReturn(Collections.emptyList());
        List<Patient> patients=patientService.getAllPatients();
        assertTrue(patients.isEmpty());
    }

    @Test
    void createPatient(){
        when(patientRepository.save(any(Patient.class))).thenReturn(patient1);
        Patient savedPatient=patientService.createPatient(patient1);


        assertNotNull(savedPatient);

        assertEquals("arunesh",savedPatient.getName());
        verify(patientRepository,times(1)).save(patient1);

    }

    @Test
    void createPatient_partialinfo(){
        Patient patient=new Patient();
        patient.setId(null);
        patient.setName("arun");
        patient.setAge(-1);
        patient.setGender(null);
        patient.setContactNumber(null);
        patient.setAddress(null);
        when(patientRepository.save(patient)).thenReturn(patient);
        Patient saved=patientService.createPatient(patient);
        assertNotNull(saved);
        assertEquals("arun",saved.getName());

    }

    @Test
    void createPatient_nullfields(){
        Patient patient=new Patient();
        patient.setId(null);
        patient.setName(null);
        patient.setAge(-1);
        patient.setGender(null);
        patient.setContactNumber(null);
        patient.setAddress(null);
        when(patientRepository.save(patient)).thenReturn(patient);
        Patient saved=patientService.createPatient(patient);
        assertNull(saved.getName());
    }

    @Test
    void createPatient_nagative(){
        when(patientRepository.save(null)).thenThrow(new IllegalArgumentException("Patient is null"));
        assertThrows(IllegalArgumentException.class,()->patientService.createPatient(null));
    }

    @Test
    void getPatientById(){
        when(patientRepository.findById("1L")).thenReturn(Optional.of(patient1));
        Patient patientById = patientService.getPatientById("1L");

        assertNotNull(patientById);
        assertEquals("arunesh", patientById.getName());
        verify(patientRepository, times(1)).findById("1L");
    }

    @Test
    void getPatientById_Notfound(){
        when(patientRepository.findById("15L")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class,()->patientService.getPatientById("15L"));
        verify(patientRepository, times(1)).findById("15L");
    }


    @Test
    void getPatientById_edge(){
        assertThrows(RuntimeException.class,()->patientService.getPatientById(null));
    }


    @Test
    public void testUpdatePatient_Success() {
        String patientId = "123";
        Patient existingPatient = new Patient();
        existingPatient.setId(patientId);
        existingPatient.setName("Old Name");

        Patient updatedPatient = new Patient();
        updatedPatient.setName("New Name");

        // Mock getPatientById internally called in updatePatient
        when(patientRepository.findById(patientId)).thenReturn(Optional.of(existingPatient));
        // Mock save method to return the updatedPatient with set id
        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Patient result = patientService.updatePatient(patientId, updatedPatient);

        assertNotNull(result);
        assertEquals(patientId, result.getId());  // id is set correctly
        assertEquals("New Name", result.getName());  // updated data is correct

        verify(patientRepository).findById(patientId);
        verify(patientRepository).save(updatedPatient);
    }

    @Test
    void deletePatient(){
        patientService.deletePatient("1L");
        verify(patientRepository,times(1)).deleteById("1L");
    }
}
