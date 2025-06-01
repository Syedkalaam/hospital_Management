package com.Hospital_Management_MiniProject_2.controller;


import com.Hospital_Management_MiniProject_2.entity.Doctor;
import com.Hospital_Management_MiniProject_2.entity.Medicine;
import com.Hospital_Management_MiniProject_2.entity.Patient;
import com.Hospital_Management_MiniProject_2.service.DoctorService;
import com.Hospital_Management_MiniProject_2.service.MedicineService;
import com.Hospital_Management_MiniProject_2.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;


    @GetMapping("/add/{patientId}")
    public String showCreateForm(@PathVariable String patientId,Model model) {
        Patient patient = patientService.getPatientById(patientId);
        List<Doctor> allDoctors = doctorService.getAllDoctors();
        model.addAttribute("doctors", allDoctors);
        model.addAttribute("patient", patient);
        model.addAttribute("medicine", new Medicine());
        return "medicines/add-medicine";
    }

    @PostMapping("/add/{patientId}")
    public String addMedicinetoPatient(@PathVariable String patientId , @ModelAttribute Medicine medicine){
        medicineService.addMedicinetoPatient(patientId,medicine);
        return "redirect:/view/"+patientId;
    }


    @GetMapping("/delete/{medicineId}/{patientId}")
    public String deleteMedicine(@PathVariable String medicineId, @PathVariable String patientId) {
        medicineService.deleteMedicineFromPatient(patientId, medicineId);
        return "redirect:/view/" + patientId;
    }

}
