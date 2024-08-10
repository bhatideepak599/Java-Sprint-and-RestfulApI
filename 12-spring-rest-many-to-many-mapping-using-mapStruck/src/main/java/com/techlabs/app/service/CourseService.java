package com.techlabs.app.service;

import java.util.List;

import com.techlabs.app.dtos.CourseDto;
import com.techlabs.app.entities.Course;

import jakarta.validation.Valid;


public interface CourseService {

	CourseDto addCourse(  CourseDto course);

	List<CourseDto> getAllCourses();

	CourseDto getCourseById(int id);

	String deleteCourseById(int id);
	
	

}
