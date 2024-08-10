package com.techlabs.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techlabs.app.dtos.CourseDto;
import com.techlabs.app.entities.Course;
import com.techlabs.app.mapping.CourseMapper;
import com.techlabs.app.repository.CourseRepository;
import com.techlabs.app.repository.StudentRepository;


@Service
public class CourseServiceImpl implements CourseService{
	private StudentRepository studentRepository;
	private CourseRepository courseRepository;
	@Autowired
	private CourseMapper courseMapper;

	public CourseServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository) {
		super();
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
	}
	@Override
	public CourseDto addCourse( CourseDto course) {
		Course courseDtoToCourse = courseMapper.courseDtoToCourse(course);
		Course save = courseRepository.save(courseDtoToCourse);
		return courseMapper.courseToCourseDto( save);
	}
	@Override
	public List<CourseDto> getAllCourses() {
		 List<Course> all = courseRepository.findAll();
		 List<CourseDto> list=getList(all);
		 return list;
	}
	
	@Override
	public CourseDto getCourseById(int id) {
		Optional<Course> byId = courseRepository.findById(id);
		if(byId.isPresent()) {
			Course course = byId.get();
			return courseMapper.courseToCourseDto(course);
		}
		
		return null;
	}
	@Override
	public String deleteCourseById(int id) {
		Course course = courseRepository.findById(id).get();
		if(course!=null) {
			courseRepository.delete(course);
			return "Course Delete Successfully";
		}
		
		return "No Course Found with Course Id :"+id;
	}
	
	private List<CourseDto> getList(List<Course> all) {
		 List<CourseDto> list=new ArrayList<>();
		 for(Course c:all) {
			 list.add(courseMapper.courseToCourseDto(c));
		 }
		 return list;
	}
	
	

}
