package com.manage.clinicBack.Dao;


import com.manage.clinicBack.module.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface StaffDao extends JpaRepository<Staff, Integer> {

    Staff findByEmailId(@Param("email") String email);



}
