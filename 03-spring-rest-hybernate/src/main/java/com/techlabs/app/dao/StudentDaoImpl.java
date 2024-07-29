package com.techlabs.app.dao;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.techlabs.app.entity.Student;
import com.techlabs.app.exception.StudentNotFoundException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
public class StudentDaoImpl implements StudentDao {
	private EntityManager entityManager;

	public StudentDaoImpl(EntityManager entityManager) {
		super();
		this.entityManager = entityManager;
	}

	@Override
	public List<Student> getAllStudent() {

		TypedQuery<Student> query = entityManager.createQuery("select s from Student s", Student.class);
		List<Student> lst = query.getResultList();

		return lst;
	}

	@Override
	public Student getStudentById(int id) {
		Student student=entityManager.find(Student.class,id);
		return student;
	}

	@Override
	@Transactional
	public Student saveAndUpdate(Student student) {
		return entityManager.merge(student);
		
	}

	@Override
	@Transactional
	public void deleteStudent(int id) {
		Student student=entityManager.find(Student.class,id);
		if(student==null) {
			 throw new StudentNotFoundException("Student with id= "+ id+" is not found");
			 
		}
		entityManager.remove(student);
		
	}

}
