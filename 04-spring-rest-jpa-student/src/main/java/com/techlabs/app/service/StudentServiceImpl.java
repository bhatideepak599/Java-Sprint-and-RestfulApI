package com.techlabs.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.techlabs.app.entity.Student;
import com.techlabs.app.repository.StudentRepository;

@Service
public class StudentServiceImpl implements StudentService {
	private StudentRepository studentDao;
	public StudentServiceImpl(StudentRepository studentDao) {
		super();
		this.studentDao = studentDao;
	}

	@Override
	public List<Student> getAllStudent() {
		return studentDao.findAll();
	}

	@Override
	public Student getStudentById(int id) {
		Optional<Student> byId = studentDao.findById(id);
		if(byId.isPresent()) return byId.get();
		return null;
		//return studentDao.findById(id).orElse(null);
	}

	@Override
	public Student saveAndUpdate(Student student) {

		return studentDao.save(student);
	}

	@Override
	public void deleteStudent(int id) {
			studentDao.deleteById(id);
	}

}
