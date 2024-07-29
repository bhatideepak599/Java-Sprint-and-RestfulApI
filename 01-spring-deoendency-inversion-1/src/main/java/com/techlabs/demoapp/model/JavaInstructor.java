package com.techlabs.demoapp.model;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


//@Component 
public class JavaInstructor implements Instructor {
	private Resource resource;
	
//	public JavaInstructor(@Qualifier(value="audioResource")Resource resource) {
//		super();
//		this.resource = resource;
//	}
	
	@Override
	public String getTrainingPlan() {
		
		return "Practice java and Spring";
	}
	@Override
	public String getResource() {
		
		return this.resource.getResource();
	}

}
