package com.Hospital_Management_MiniProject_2.service;

import com.Hospital_Management_MiniProject_2.entity.Doctor;
import com.Hospital_Management_MiniProject_2.entity.Medicine;
import com.Hospital_Management_MiniProject_2.entity.Patient;
import com.Hospital_Management_MiniProject_2.exception.ResourceNotFoundException;
import com.Hospital_Management_MiniProject_2.repository.DoctorRepository;
import com.Hospital_Management_MiniProject_2.repository.MedicineRepository;
import com.Hospital_Management_MiniProject_2.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.mockito.InjectMocks;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MedicineServiceTest {

    @InjectMocks
    private MedicineService medicineService;

    @Mock
    private MedicineRepository medicineRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private DoctorRepository doctorRepository;

    private Medicine medicine;
    private Patient patient;
    private Doctor doctor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        medicine = new Medicine();
        medicine.setId("m1");
        medicine.setDoctorId("d1");

        patient = new Patient();
        patient.setId("p1");

        doctor = new Doctor();
        doctor.setId("d1");
    }

    @Test
    void testCreateMedicine() {
        when(medicineRepository.save(medicine)).thenReturn(medicine);
        Medicine result = medicineService.createMedicine(medicine);
        assertEquals(medicine, result);
    }

    @Test
    void testGetAllMedicines() {
        List<Medicine> medicines = List.of(medicine);
        when(medicineRepository.findAll()).thenReturn(medicines);
        assertEquals(medicines, medicineService.getAllMedicines());
    }

    @Test
    void testGetMedicineById_WhenExists() {
        when(medicineRepository.findById("m1")).thenReturn(Optional.of(medicine));
        Medicine result = medicineService.getMedicineById("m1");
        assertEquals(medicine, result);
    }

    @Test
    void testGetMedicineById_WhenNotFound() {
        when(medicineRepository.findById("invalid")).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> medicineService.getMedicineById("invalid"));
    }

    @Test
    void testAddMedicineToPatient() {
        when(patientRepository.findById("p1")).thenReturn(Optional.of(patient));
        when(doctorRepository.findById("d1")).thenReturn(Optional.of(doctor));
        when(medicineRepository.save(medicine)).thenReturn(medicine);

        medicineService.addMedicinetoPatient("p1", medicine);

        verify(medicineRepository).save(medicine);
        verify(patientRepository).save(patient);
        verify(doctorRepository).save(doctor);
        assertTrue(patient.getMedicines().contains(medicine));
        assertTrue(patient.getDoctors().contains(doctor));
        assertTrue(doctor.getMedicines().contains(medicine));
        assertTrue(doctor.getPatients().contains(patient));
    }

    @Test
    void testDeleteMedicineFromPatient() {
        medicine.setDoctorId("d1");
        patient.getMedicines().add(medicine);
        doctor.getMedicines().add(medicine);
        doctor.getPatients().add(patient);

        when(patientRepository.findById("p1")).thenReturn(Optional.of(patient));
        when(medicineRepository.findById("m1")).thenReturn(Optional.of(medicine));
        when(doctorRepository.findById("d1")).thenReturn(Optional.of(doctor));

        medicineService.deleteMedicineFromPatient("p1", "m1");

        verify(patientRepository).save(patient);
        verify(doctorRepository).save(doctor);
        verify(medicineRepository).deleteById("m1");

        assertFalse(patient.getMedicines().contains(medicine));
        assertFalse(doctor.getMedicines().contains(medicine));
        assertFalse(doctor.getPatients().contains(patient));
    }
}
