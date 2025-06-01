package com.Hospital_Management_MiniProject_2.controller;

import com.Hospital_Management_MiniProject_2.entity.Patient;
import com.Hospital_Management_MiniProject_2.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;


import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private  MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @InjectMocks
    private  PatientController patientController;

    private Patient patient1;
    private Patient patient2;
    private List<Patient> patients;

    @BeforeEach
    void setup(){

        patient1 = new Patient();
        patient1.setId("1l");
        patient1.setName("arunesh");
        patient1.setAge(23);
        patient1.setGender("male");
        patient1.setContactNumber("7094950916");
        patient1.setAddress("chennai");

        patient2 = new Patient("2l","arjun",22,"male","9094950916","salem",null,null,null);

         patients = Arrays.asList(patient1, patient2);

    }

    @Test
    void listPatients() throws Exception {
        when(patientService.getAllPatients()).thenReturn(patients);


        mockMvc.perform(get("/patients"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/list"))
                .andExpect(model().attributeExists("patients"))
                .andExpect(model().attribute("patients",patients));

    }

    @Test
    void  viewpatient() throws Exception{
        when(patientService.getPatientById("1l")).thenReturn(patient1);

        mockMvc.perform(get("/view/1l"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/view"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attribute("patient",patient1));
    }

    @Test
    void showCreateForm_ShouldReturnCreateViewWithEmptyPatient() throws Exception {
        mockMvc.perform(get("/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/create"))
                .andExpect(model().attributeExists("patient"));
    }

    @Test
    void createPatient_ShouldRedirectToPatients() throws Exception {
        mockMvc.perform(post("/create")
                        .param("name", "John Doe")
                        .param("age", "30")
                        .param("gender", "Male")
                        .param("contactNumber", "1234567890")
                        .param("address", "123 Main St"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"));
    }

    @Test
    void showEditForm_ShouldReturnEditViewWithPatient() throws Exception {

        when(patientService.getPatientById("1l")).thenReturn(patient1);
        mockMvc.perform(get("/edit/1l"))
                .andExpect(status().isOk())
                .andExpect(view().name("patients/edit"))
                .andExpect(model().attributeExists("patient"))
                .andExpect(model().attribute("patient", patient1));
    }

    @Test
    void updatePatient_ShouldRedirectToPatients() throws Exception {
        mockMvc.perform(post("/update/1l")
                        .param("name", "John Doe")
                        .param("age", "30")
                        .param("gender", "Male")
                        .param("contactNumber", "1234567890")
                        .param("address", "123 Main St"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"));


        verify(patientService).updatePatient(eq("1l"), any(Patient.class));
    }

    @Test
    void deletePatient_ShouldRedirectToPatients() throws Exception {
        mockMvc.perform(get("/delete/2l"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/patients"));

        verify(patientService).deletePatient("2l");
    }


}

