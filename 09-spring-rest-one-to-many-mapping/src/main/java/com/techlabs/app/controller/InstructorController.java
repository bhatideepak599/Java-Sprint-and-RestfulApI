package com.techlabs.app.controller;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.entity.Course;
import com.techlabs.app.entity.Instructor;
import com.techlabs.app.service.CourseService;
import com.techlabs.app.service.InstructorService;

@RestController
@RequestMapping("/api/instructor")
public class InstructorController {

	private InstructorService instructorService;
	private CourseService courseService;

	public InstructorController(InstructorService instructorService, CourseService courseService) {
		super();
		this.instructorService = instructorService;
		this.courseService = courseService;
	}

	@GetMapping
	public List<Instructor> getAll() {
		return instructorService.getAll();
	}

	@PostMapping
	public Instructor addInstructor(@RequestBody Instructor instructor) {
		return instructorService.save(instructor);
	}

	@PostMapping("/course")
	public Course addCourse(@RequestBody Course course) {
		return courseService.addCourse(course);
	}

	@PutMapping
	public Instructor updateInstructor(@RequestBody Instructor instructor) {
		return instructorService.save(instructor);
	}

	@GetMapping("/{id}")
	public Instructor getInstructorById(@PathVariable(name = "id") int id) {
		return instructorService.getInstructorById(id);
	}

	@GetMapping("/course/{id}")
	public List<Instructor> getInstructorByCourseId(@PathVariable(name = "id") int id) {
		return instructorService.getInstructorByCourseId(id);
	}

	@DeleteMapping("/{id}")
	public void deleteInstructorById(@PathVariable(name = "id") int id) {
		instructorService.deleteInstructorById(id);
	}

	@PutMapping("/assign/course-to/ins/{iId}/{cId}")
	public Instructor assignCourseToInstructor(@PathVariable(name = "iId") int iId,
			@PathVariable(name = "cId") int cId) {
		return instructorService.assignCourseToInstructor(iId, cId);
	}

	@PutMapping("/remove/course-from/ins/{iId}/{cId}")
	public Instructor removeCoursefromInstructor(@PathVariable(name = "iId") int iId,
			@PathVariable(name = "cId") int cId) {
		Instructor removeCoursefromInstructor = instructorService.removeCoursefromInstructor(iId, cId);
		return removeCoursefromInstructor;
	}

}
