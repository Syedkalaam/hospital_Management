package com.Hospital_Management_MiniProject_2.controller;

import com.Hospital_Management_MiniProject_2.entity.Appointment;
import com.Hospital_Management_MiniProject_2.service.AppointmentService;
import com.Hospital_Management_MiniProject_2.service.DoctorService;
import com.Hospital_Management_MiniProject_2.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AppointmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AppointmentService appointmentService;

    @Mock
    private PatientService patientService;

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private AppointmentController appointmentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(appointmentController).build();
    }

    @Test
    void testAppointmentBooking() throws Exception {
        String doctorId = "d1";

        mockMvc.perform(post("/appointments/booking/{doctorId}", doctorId)
                        .param("id", "a1")
                        .param("date", "2025-05-20")
                        .param("time", "10:00")
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/viewdoctor/" + doctorId));

        verify(appointmentService).bookAppointment(eq(doctorId), any(Appointment.class));
    }

    @Test
    void testDeleteAppointment() throws Exception {
        String doctorId = "d1";
        String appointmentIds = "a1";

        mockMvc.perform(get("/appointments/delete/{appointmentIds}/{doctorId}", appointmentIds, doctorId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/viewdoctor/" + doctorId));

        verify(appointmentService).deleteAppointment(doctorId, appointmentIds);
    }
}
