package com.techlabs.app.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.techlabs.app.entity.Student;
import com.techlabs.app.service.StudentService;

@RestController
public class StudentController {

	private StudentService studentService;

	public StudentController(StudentService studentService) {
		super();
		this.studentService = studentService;
	}

	@GetMapping("/students")
	public ResponseEntity<List<Student>> getAllStuddents() {

		return new ResponseEntity<List<Student>>(studentService.getAllStudent(), HttpStatus.OK);
	}

	@GetMapping("/students/{sid}")
	public ResponseEntity<Student> getStudentById(@PathVariable(name = "sid") int id) {

		Student student = studentService.getStudentById(id);
		if (student == null)
			throw new RuntimeException("Student not found");

		return new ResponseEntity<Student>(student, HttpStatus.CREATED);
	}

	@DeleteMapping("/students/{sid}")
	public void deleteStudent(@PathVariable(name = "sid") int id) {

		studentService.deleteStudent(id);

	}

	@PostMapping("/students")

	public Student addStudent(@RequestBody Student student) {
		student.setId(0);
		return studentService.saveAndUpdate(student);
	}

	@PutMapping("/students")
	public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
		if (student.getId() != 0)
			return new ResponseEntity<Student>(studentService.saveAndUpdate(student), HttpStatus.OK);

		return null;

	}

//	@ExceptionHandler
//	public ResponseEntity<StudentErrorResponse> handleException(StudentNotFoundException ex) {
//		// StudentErrorResponse error= new StudentErrorResponse(HttpStatus.NOT_FOUND,
//		// ex.getMessage(), System.currentTimeMillis());
//		// return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
//		StudentErrorResponse error = new StudentErrorResponse();
//
//		error.setStatus(HttpStatus.NOT_FOUND.value());
//		error.setMessage(ex.getMessage());
//		error.setTimestamp(LocalDateTime.now());
//
//		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//	}
//	
//	@ExceptionHandler
//	public ResponseEntity<StudentErrorResponse> handleException(Exception ex) {
//		// StudentErrorResponse error= new StudentErrorResponse(HttpStatus.NOT_FOUND,
//		// ex.getMessage(), System.currentTimeMillis());
//		// return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
//		StudentErrorResponse error = new StudentErrorResponse();
//
//		error.setStatus(HttpStatus.NOT_FOUND.value());
//		error.setMessage(ex.getMessage());
//		error.setTimestamp(LocalDateTime.now());
//
//		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
//	}

}
