package com.techlabs.app.service;

import java.util.List;

import com.techlabs.app.entity.Instructor;

public interface InstructorService {

	List<Instructor> getAll();

	Instructor save(Instructor instructor);

	Instructor getInstructorById(int id);

	List<Instructor> getInstructorByCourseId(int id);

	void deleteInstructorById(int id);

	Instructor assignCourseToInstructor(int iId, int cId);

	Instructor removeCoursefromInstructor(int iId, int cId);

}
