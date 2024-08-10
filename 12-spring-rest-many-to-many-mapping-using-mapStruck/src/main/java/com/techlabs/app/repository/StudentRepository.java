package com.techlabs.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.techlabs.app.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

}
