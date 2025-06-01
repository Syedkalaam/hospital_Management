package com.Hospital_Management_MiniProject_2.controller;


import com.Hospital_Management_MiniProject_2.service.DoctorService;
import com.Hospital_Management_MiniProject_2.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @GetMapping("/")
    public String index(){
        return "index";
    }


}
