package com.techlabs.demoapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

import com.techlabs.demoapp.model.Instructor;
import com.techlabs.demoapp.model.JavaInstructor;
import com.techlabs.demoapp.model.PythonInstructor;

@Configuration
public class InstructorConfig {
	@Bean(name = "pythonInstructor")
	public Instructor getPythonInstructorBean() {
		return new PythonInstructor(null);
	}

	@Bean(name = "javaInstructor")
	@Primary
	@Scope("prototype")
	public Instructor getJavaInstructorBean() {
		return new JavaInstructor();
	}
}
