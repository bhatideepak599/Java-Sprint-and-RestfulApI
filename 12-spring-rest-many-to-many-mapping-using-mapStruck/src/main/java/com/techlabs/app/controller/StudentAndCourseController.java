package com.techlabs.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.dtos.CourseDto;
import com.techlabs.app.dtos.StudentDto;
import com.techlabs.app.entities.Course;
import com.techlabs.app.entities.Student;
import com.techlabs.app.service.CourseService;
import com.techlabs.app.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class StudentAndCourseController {
	private final StudentService studentService;
	private final CourseService courseService;

	public StudentAndCourseController(StudentService studentService, CourseService courseService) {
		this.studentService = studentService;
		this.courseService = courseService;
	}

	@PostMapping("/students")
	public ResponseEntity<StudentDto> addStudent(@Valid @RequestBody StudentDto student) {

		StudentDto addedStudent = studentService.addStudent(student);
		return new ResponseEntity<>(addedStudent, HttpStatus.CREATED);
	}

	@GetMapping("/students")
	public ResponseEntity<List<StudentDto>> getAllStudents() {
		List<StudentDto> list = studentService.getAllStudents();
		if (list.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/students/{id}")
	public ResponseEntity<StudentDto> getStudentById(@PathVariable("id") int id) {

		StudentDto getStudent = studentService.getStudentById(id);
		return new ResponseEntity<>(getStudent, HttpStatus.OK);
	}

	@DeleteMapping("/students/{id}")
	public ResponseEntity<String> deleteSttudentById(@PathVariable("id") int id) {

		String message = studentService.deleteSttudentById(id);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	@PostMapping("/students/{studentId}/courses/{courseId}")
	public ResponseEntity<StudentDto> addStudentToACourse(@PathVariable("studentId") int studentId,
			@PathVariable("courseId") int courseId) {

		StudentDto getStudent = studentService.addStudentToACourse(studentId, courseId);
		return new ResponseEntity<>(getStudent, HttpStatus.OK);
	}

	@PostMapping("/courses")

	public ResponseEntity<CourseDto> addCourse(@Valid @RequestBody CourseDto course) {

		CourseDto addCourse = courseService.addCourse(course);
		return new ResponseEntity<>(addCourse, HttpStatus.OK);
	}

	@GetMapping("/courses")
	public ResponseEntity<List<CourseDto>> getAllCourses() {
		List<CourseDto> list = courseService.getAllCourses();
		if (list.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("courses/{id}")
	public ResponseEntity<CourseDto> getCourseById(@PathVariable("id") int id) {

		CourseDto getCourse = courseService.getCourseById(id);
		return new ResponseEntity<>(getCourse, HttpStatus.OK);
	}

	@DeleteMapping("courses/{id}")
	public ResponseEntity<String> deleteCourseById(@PathVariable("id") int id) {

		String getCourse = courseService.deleteCourseById(id);
		return new ResponseEntity<>(getCourse, HttpStatus.OK);
	}

	@DeleteMapping("students/{studentId}/courses/{courseId}")
	public ResponseEntity<StudentDto> removeStudentToACourse(@PathVariable("studentId") int studentId,
			@PathVariable("courseId") int courseId) {

		StudentDto message = studentService.removeStudentFromACourse(studentId, courseId);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
}
