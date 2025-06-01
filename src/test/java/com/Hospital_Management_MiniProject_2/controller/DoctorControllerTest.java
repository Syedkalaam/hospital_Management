package com.Hospital_Management_MiniProject_2.controller;

import com.Hospital_Management_MiniProject_2.entity.Doctor;
import com.Hospital_Management_MiniProject_2.entity.Patient;
import com.Hospital_Management_MiniProject_2.service.DoctorService;
import com.Hospital_Management_MiniProject_2.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private DoctorController doctorController;

    private MockMvc mockMvc;

    private Doctor doctor;
    private Patient patient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(doctorController).build();

        doctor = new Doctor();
        doctor.setId("d1");
        doctor.setName("Dr. John");

        patient = new Patient();
        patient.setId("p1");
        patient.setName("Jane Doe");
    }

    @Test
    void testListDoctors() throws Exception {
        List<Doctor> doctors = List.of(doctor);
        List<Patient> patients = List.of(patient);

        when(doctorService.getAllDoctors()).thenReturn(doctors);
        when(patientService.getAllPatients()).thenReturn(patients);

        mockMvc.perform(get("/doctors"))
                .andExpect(status().isOk())
                .andExpect(view().name("/doctors/list"))
                .andExpect(model().attribute("doctors", doctors))
                .andExpect(model().attribute("patients", patients));

        verify(doctorService).getAllDoctors();
        verify(patientService).getAllPatients();
    }

    @Test
    void testShowCreateForm() throws Exception {
        mockMvc.perform(get("/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("/doctors/add-doctor"))
                .andExpect(model().attributeExists("doctor"));
    }

    @Test
    void testViewDoctor() throws Exception {
        when(doctorService.getDoctorById("d1")).thenReturn(doctor);

        mockMvc.perform(get("/viewdoctor/d1"))
                .andExpect(status().isOk())
                .andExpect(view().name("/doctors/view"))
                .andExpect(model().attribute("doctor", doctor));

        verify(doctorService).getDoctorById("d1");
    }

    @Test
    void testCreateDoctor() throws Exception {
        // Just verify redirect and service call, no validation errors here
        mockMvc.perform(post("/add")
                        .param("id", "d1")
                        .param("name", "Dr. John")
                        .param("specialization", "Cardiology"))

                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctors"));

        verify(doctorService).createDoctor(any(Doctor.class));
    }

    @Test
    void testDeleteDoctor() throws Exception {
        mockMvc.perform(get("/doctor/delete/d1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctors"));

        verify(doctorService).deleteDoctor("d1");
    }
}
