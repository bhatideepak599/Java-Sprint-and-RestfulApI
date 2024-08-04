package com.techlabs.app.service;

import org.springframework.stereotype.Service;

import com.techlabs.app.entity.Course;

import com.techlabs.app.repository.CourseRepository;
import com.techlabs.app.repository.InstructorRepository;

@Service
public class CourseServiceImpl implements CourseService {
	private InstructorRepository instructorRepository;
	private CourseRepository courseRepository;

	public CourseServiceImpl(InstructorRepository instructorRepository, CourseRepository courseRepository) {
		super();
		this.instructorRepository = instructorRepository;
		this.courseRepository = courseRepository;
	}

	@Override
	public Course addCourse(Course course) {
		return courseRepository.save(course);
	}
}
