package com.techlabs.hybernate.dao;

import java.util.List;

import com.techlabs.hybernate.entity.Student;

public interface StudentDao {
	public void save(Student student);

	public List<Student> getAllStudents();

	public Student getStudentById(int i);

	public List<Student> getStudentByFirstName(String string);

	public List<Student> getStudentByFirstNameAndLastName(String string, String string2);

	public void updateStudent(Student s);

	public void deleteStudent(int i);

	public void updateWithoutUsingMerge(Student st);

	public void deleteStudentIdLessthan2(int i);
}
