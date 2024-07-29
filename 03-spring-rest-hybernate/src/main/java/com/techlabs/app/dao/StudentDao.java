package com.techlabs.app.dao;

import java.util.List;

import com.techlabs.app.entity.Student;

public interface StudentDao {
	public List<Student> getAllStudent();

	public Student getStudentById(int id);

	public Student saveAndUpdate(Student student);

	public void deleteStudent(int id);
}
