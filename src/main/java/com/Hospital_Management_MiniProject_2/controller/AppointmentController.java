package com.Hospital_Management_MiniProject_2.controller;


import com.Hospital_Management_MiniProject_2.entity.Appointment;
import com.Hospital_Management_MiniProject_2.service.AppointmentService;
import com.Hospital_Management_MiniProject_2.service.DoctorService;
import com.Hospital_Management_MiniProject_2.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public String listAppointments(Model model) {
        List<Appointment> allAppointments = appointmentService.getAllAppointments();
        model.addAttribute("appointments",allAppointments);
        return "/list";
    }



    @PostMapping("/booking/{doctorId}")
    public String appointmentBooking(@PathVariable("doctorId") String doctorId,@ModelAttribute Appointment appointment){
        appointmentService.bookAppointment(doctorId,appointment);
        return "redirect:/viewdoctor/"+doctorId;
    }

    @GetMapping("/delete/{appointmentIds}/{doctorId}")
    public String deleteAppointment(@PathVariable String appointmentIds, @PathVariable String doctorId) {
        appointmentService.deleteAppointment(doctorId, appointmentIds);
        return "redirect:/viewdoctor/" + doctorId;
    }
}
