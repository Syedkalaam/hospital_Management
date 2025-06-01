package com.Hospital_Management_MiniProject_2.repository;


import com.Hospital_Management_MiniProject_2.entity.Medicine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataMongoTest
public class MedicineRepositoryTest {

    @Autowired
    private MedicineRepository medicineRepository;

    private Medicine medicine;

    @BeforeEach
    void setUp() {
        medicineRepository.deleteAll();

        medicine = new Medicine();
        medicine.setId("med123");
        medicine.setName("Ibuprofen");
        medicine.setPatientId("patient001");
        medicine.setDoctorId("doctor001");

        medicineRepository.save(medicine);
    }

    @Test
    void addMedicine_ShouldSaveAndReturnMedicine() {
        Medicine saved = medicineRepository.findById("med123").orElse(null);

        assertThat(saved).isNotNull();
        assertThat(saved.getName()).isEqualTo("Ibuprofen");
        assertThat(saved.getPatientId()).isEqualTo("patient001");
        assertThat(saved.getDoctorId()).isEqualTo("doctor001");
    }

    @Test
    void deleteMedicine_ShouldRemoveMedicineFromRepository() {
        // Ensure the medicine exists before deletion
        Medicine existing = medicineRepository.findById("med123").orElse(null);
        assertThat(existing).isNotNull();

        // Delete the medicine
        medicineRepository.deleteById("med123");

        // Ensure it's deleted
        Medicine deleted = medicineRepository.findById("med123").orElse(null);
        assertThat(deleted).isNull();
    }
}
//package com.Hospital_Management_MiniProject_2.repository;
//
//
//import com.Hospital_Management_MiniProject_2.entity.Medicine;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//@DataMongoTest
//public class MedicineRepositoryTest {
//
//    @Autowired
//    private MedicineRepository medicineRepository;
//
//    private Medicine medicine;
//
//    @BeforeEach
//    void setUp() {
//        medicineRepository.deleteAll();
//
//        medicine = new Medicine();
//        medicine.setId("med123");
//        medicine.setName("Ibuprofen");
//        medicine.setPatientId("patient001");
//        medicine.setDoctorId("doctor001");
//
//        medicineRepository.save(medicine);
//    }
//
//    @Test
//    void addMedicine_ShouldSaveAndReturnMedicine() {
//        Medicine saved = medicineRepository.findById("med123").orElse(null);
//
//        assertThat(saved).isNotNull();
//        assertThat(saved.getName()).isEqualTo("Ibuprofen");
//        assertThat(saved.getPatientId()).isEqualTo("patient001");
//        assertThat(saved.getDoctorId()).isEqualTo("doctor001");
//    }
//
//    @Test
//    void deleteMedicine_ShouldRemoveMedicineFromRepository() {
//        // Ensure the medicine exists before deletion
//        Medicine existing = medicineRepository.findById("med123").orElse(null);
//        assertThat(existing).isNotNull();
//
//        // Delete the medicine
//        medicineRepository.deleteById("med123");
//
//        // Ensure it's deleted
//        Medicine deleted = medicineRepository.findById("med123").orElse(null);
//        assertThat(deleted).isNull();
//    }
