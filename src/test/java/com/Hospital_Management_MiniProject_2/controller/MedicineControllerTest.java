package com.Hospital_Management_MiniProject_2.controller;

import com.Hospital_Management_MiniProject_2.entity.Doctor;
import com.Hospital_Management_MiniProject_2.entity.Medicine;
import com.Hospital_Management_MiniProject_2.entity.Patient;
import com.Hospital_Management_MiniProject_2.service.DoctorService;
import com.Hospital_Management_MiniProject_2.service.MedicineService;
import com.Hospital_Management_MiniProject_2.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(MedicineController.class)
class MedicineControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private MedicineService medicineService;

    @MockBean
    private PatientService patientService;

    @MockBean
    private DoctorService doctorService;

    @MockBean
    private Model model;

    @InjectMocks
    private MedicineController medicineController;

    private Patient patient;
    private Medicine medicine;
    private Doctor doctor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(medicineController).build();

        patient = new Patient();
        patient.setId("p1");
        patient.setName("John Doe");

        doctor = new Doctor();
        doctor.setId("d1");
        doctor.setName("Dr. Smith");

        medicine = new Medicine();
        medicine.setId("m1");
        medicine.setDoctorId("d1");
        medicine.setPatientId("p1");
    }

    @Test
    void testShowCreateForm() {
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(doctor);

        when(patientService.getPatientById("p1")).thenReturn(patient);
        when(doctorService.getAllDoctors()).thenReturn(doctors);

        String view = medicineController.showCreateForm("p1", model);

        verify(model).addAttribute("doctors", doctors);
        verify(model).addAttribute("patient", patient);
        verify(model).addAttribute(eq("medicine"), any(Medicine.class));
        assertEquals("medicines/add-medicine", view);
    }

    @Test
    void testAddMedicinetoPatient() {
        String view = medicineController.addMedicinetoPatient("p1", medicine);
        verify(medicineService).addMedicinetoPatient("p1", medicine);
        assertEquals("redirect:/view/p1", view);
    }

    @Test
    void testDeleteMedicine() {
        String view = medicineController.deleteMedicine("m1", "p1");
        verify(medicineService).deleteMedicineFromPatient("p1", "m1");
        assertEquals("redirect:/view/p1", view);
    }
}
