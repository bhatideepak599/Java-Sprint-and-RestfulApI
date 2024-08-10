
package com.techlabs.app.service;

import java.util.List;

import com.techlabs.app.dtos.StudentDto;

public interface StudentService {

	StudentDto addStudent(StudentDto student);

	List<StudentDto> getAllStudents();

	StudentDto getStudentById(int id);

	String deleteSttudentById(int id);

	StudentDto addStudentToACourse(int studentId, int courseId);

	StudentDto removeStudentFromACourse(int studentId, int courseId);

}
