package com.techlabs.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.techlabs.app.dto.CourseDto;
import com.techlabs.app.entity.Course;
import com.techlabs.app.repository.CourseRepository;
import com.techlabs.app.repository.StudentRepository;


@Service
public class CourseServiceImpl implements CourseService{
	private StudentRepository studentRepository;
	private CourseRepository courseRepository;

	public CourseServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository) {
		super();
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
	}
	@Override
	public Course addCourse( Course course) {
		Course save = courseRepository.save(course);
		return save;
	}
	@Override
	public List<Course> getAllCourses() {
		return courseRepository.findAll();
	}
	@Override
	public Course getCourseById(int id) {
		Optional<Course> byId = courseRepository.findById(id);
		if(byId.isPresent()) {
			return byId.get();
		}
		
		return null;
	}
	@Override
	public String deleteCourseById(int id) {
		Course course = getCourseById(id);
		if(course!=null) {
			courseRepository.delete(course);
			return "Course Delete Successfully";
		}
		
		return "No Course Found with Course Id :"+id;
	}
	@Override
	public Course courseDtoToCourse(CourseDto courseDto) {
		Course course =new Course();
		course.setId(courseDto.getId());
		course.setTitle(courseDto.getTitle());
		return course;
	}
	
	
	

}
