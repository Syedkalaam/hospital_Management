package com.Hospital_Management_MiniProject_2.service;

import com.Hospital_Management_MiniProject_2.entity.Doctor;
import com.Hospital_Management_MiniProject_2.entity.Medicine;
import com.Hospital_Management_MiniProject_2.entity.Patient;
import com.Hospital_Management_MiniProject_2.exception.ResourceNotFoundException;
import com.Hospital_Management_MiniProject_2.repository.DoctorRepository;
import com.Hospital_Management_MiniProject_2.repository.MedicineRepository;
import com.Hospital_Management_MiniProject_2.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    public Medicine createMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    public Medicine getMedicineById(String id) {
        return medicineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with id: " + id));
    }

    public void addMedicinetoPatient(String patinetId, Medicine medicine){
        Patient patient = patientRepository.findById(patinetId).orElseThrow(()->{
            throw new ResourceNotFoundException("Patient not found");
        });
        String doctorId=medicine.getDoctorId();
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(()->{
                    throw new ResourceNotFoundException("Doctor not found");
                });
        medicine.setPatientId(patinetId);
        medicine = medicineRepository.save(medicine);
        doctor.getMedicines().add(medicine);
        doctor.getPatients().add(patient);
        patient.getMedicines().add(medicine);
        patient.getDoctors().add(doctor);
        patientRepository.save(patient);
        doctorRepository.save(doctor);
    }

    public void deleteMedicineFromPatient(String patientId, String medicineId) {
        Optional<Patient> optionalPatient = patientRepository.findById(patientId);
        Optional<Medicine> optionalMedicine = medicineRepository.findById(medicineId);


        if (optionalPatient.isPresent() && optionalMedicine.isPresent()) {
            Patient patient = optionalPatient.get();
            Medicine medicine = optionalMedicine.get();
            Doctor doctor=doctorRepository.findById(medicine.getDoctorId()).orElseThrow(()->{throw new ResourceNotFoundException("Doctor not found");});

            //Remove medicine reference from Doctor
            doctor.getMedicines().removeIf(med->med.getId().equals(medicineId));
            doctor.getPatients().removeIf(existpatient -> existpatient.getId().equals(patientId));
            doctorRepository.save(doctor);
            // Remove medicine reference from patient
            patient.getMedicines().removeIf(med -> med.getId().equals(medicineId));
            patient.getDoctors().removeIf(doc->doc.getId().equals(medicine.getDoctorId()));
            patientRepository.save(patient);



            // Delete the medicine document
            medicineRepository.deleteById(medicineId);
        } else {
            throw new IllegalArgumentException("Patient or medicine not found");
        }
    }
}
