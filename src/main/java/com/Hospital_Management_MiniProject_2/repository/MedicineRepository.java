package com.Hospital_Management_MiniProject_2.repository;

import com.Hospital_Management_MiniProject_2.entity.Medicine;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface MedicineRepository extends MongoRepository<Medicine,String> {


}
