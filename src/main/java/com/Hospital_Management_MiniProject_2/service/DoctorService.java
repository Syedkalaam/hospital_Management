package com.Hospital_Management_MiniProject_2.service;

import com.Hospital_Management_MiniProject_2.entity.Doctor;
import com.Hospital_Management_MiniProject_2.exception.ResourceNotFoundException;
import com.Hospital_Management_MiniProject_2.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }



    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(String id) {

        return doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with id: " + id));
    }

    public Doctor updateDoctor(String id, Doctor updatedDoctor) {
        getDoctorById(id);
        updatedDoctor.setId(id);
        return doctorRepository.save(updatedDoctor);
    }

    public void deleteDoctor(String id) {
        getDoctorById(id);


        doctorRepository.deleteById(id);
    }
}
