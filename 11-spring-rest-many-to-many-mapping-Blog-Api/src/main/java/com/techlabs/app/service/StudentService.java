package com.techlabs.app.service;

import java.util.List;

import com.techlabs.app.dto.StudentDto;
import com.techlabs.app.entity.Student;


public interface StudentService {

	StudentDto addStudent( StudentDto student);

	List<StudentDto> getAllStudents();

	Student getStudentById(int id);

	String deleteSttudentById(int id);

	Student addStudentToACourse(int studentId, int courseId);

	StudentDto removeStudentFromACourse(int studentId, int courseId);


}
