package com.Hospital_Management_MiniProject_2.controller;


import com.Hospital_Management_MiniProject_2.entity.Doctor;
import com.Hospital_Management_MiniProject_2.entity.Patient;
import com.Hospital_Management_MiniProject_2.service.DoctorService;
import com.Hospital_Management_MiniProject_2.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @GetMapping("doctors")
    public String listDoctors(Model model) {
        List<Doctor> allDoctors = doctorService.getAllDoctors();
        List<Patient> allPatient= patientService.getAllPatients();
        model.addAttribute("doctors", allDoctors);
        model.addAttribute("patients",allPatient);
        return "doctors/list";
    }

    @GetMapping("/add")
    public String showCreateForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctors/add-doctor";
    }
    @GetMapping("/viewdoctor/{id}")
    public String viewDoctor(@PathVariable String id, Model model) {
        Doctor doctor = doctorService.getDoctorById(id);
        model.addAttribute("doctor",doctor);
        return "doctors/view";
    }

    @PostMapping("/add")
    public String createDoctor(@Valid @ModelAttribute Doctor doctor) {
        doctorService.createDoctor(doctor);
        return "redirect:/doctors";
    }


    @GetMapping("/doctor/delete/{id}")
    public String deleteDoctor(@PathVariable String id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctors";
    }
}
