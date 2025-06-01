package com.Hospital_Management_MiniProject_2.repository;

import com.Hospital_Management_MiniProject_2.entity.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;




@Repository
public interface AppointmentRepository extends MongoRepository<Appointment,String> {

}
