package com.techlabs.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techlabs.app.entity.Course;
import com.techlabs.app.entity.Instructor;
import com.techlabs.app.repository.CourseRepository;
import com.techlabs.app.repository.InstructorRepository;

@Service
public class InstructorServiceImpl implements InstructorService {
	private InstructorRepository instructorRepository;
	private CourseRepository courseRepository;

	public InstructorServiceImpl(InstructorRepository instructorRepository, CourseRepository courseRepository) {
		super();
		this.instructorRepository = instructorRepository;
		this.courseRepository = courseRepository;
	}

	@Override
	public List<Instructor> getAll() {
		return instructorRepository.findAll();
	}

	@Override
	public Instructor save(Instructor instructor) {
		// if(instructor.getId()==0) return null;
		return instructorRepository.save(instructor);
	}

	@Override
	public Instructor getInstructorById(int id) {
		return instructorRepository.findById(id).get();
	}

	@Override
	public List<Instructor> getInstructorByCourseId(int id) {
		return null;
		// return courseRepository.findAllById(id);
	}

	@Override
	public void deleteInstructorById(int id) {
		Instructor instructor = getInstructorById(id);
		List<Course> courses = instructor.getCourses();
		for (Course c : courses) {
			c.setInstructor(null);
		}
		instructorRepository.delete(instructor);

	}

	@Override
	public Instructor assignCourseToInstructor(int iId, int cId) {
		Instructor ins = getInstructorById(iId);
		Course c = courseRepository.findById(cId).get();
		c.setInstructor(ins);
		courseRepository.save(c);

		List<Course> courses = ins.getCourses();
		courses.add(c);
		ins.setCourses(courses);

		return instructorRepository.save(ins);

	}

	@Override
	public Instructor removeCoursefromInstructor(int iId, int cId) {
		
		Instructor ins = getInstructorById(iId);
		Course c = courseRepository.findById(cId).get();
		c.setInstructor(null);
		courseRepository.save(c);

		List<Course> courses = ins.getCourses();
		courses.remove(c);
		ins.setCourses(courses);

		return instructorRepository.save(ins);
	}

}
