package com.techlabs.demoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.demoapp.model.Instructor;

@RestController
public class InstructorController {
	private Instructor instructor;
	
	

//	public InstructorController(@Qualifier(value="javaInstructor") Instructor instructor) {
//		super();
//		this.instructor = instructor;
//	}
//	
	@Autowired
	@Qualifier(value="pythonInstructor")
	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}


	@GetMapping("/get-train")
	public String getTrainingPlan() {
		return this.instructor.getTrainingPlan()+"\n" +this.instructor.getResource();
	}
}
