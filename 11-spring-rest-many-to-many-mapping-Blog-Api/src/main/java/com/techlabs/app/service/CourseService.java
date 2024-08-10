package com.techlabs.app.service;

import java.util.List;

import com.techlabs.app.dto.CourseDto;
import com.techlabs.app.entity.Course;


public interface CourseService {

	Course addCourse( Course course);

	List<Course> getAllCourses();

	Course getCourseById(int id);

	String deleteCourseById(int id);
	
	Course courseDtoToCourse(CourseDto corseDto); 

}
