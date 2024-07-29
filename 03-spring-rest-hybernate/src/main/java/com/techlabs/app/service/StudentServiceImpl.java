package com.techlabs.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techlabs.app.dao.StudentDao;
import com.techlabs.app.entity.Student;

@Service
public class StudentServiceImpl implements StudentService {
	private StudentDao studentDao;
	public StudentServiceImpl(StudentDao studentDao) {
		super();
		this.studentDao = studentDao;
	}

	@Override
	public List<Student> getAllStudent() {
		return studentDao.getAllStudent();
	}

	@Override
	public Student getStudentById(int id) {

		return studentDao.getStudentById(id);
	}

	@Override
	public Student saveAndUpdate(Student student) {

		return studentDao.saveAndUpdate(student);
	}

	@Override
	public void deleteStudent(int id) {
			studentDao.deleteStudent(id);
	}

}
