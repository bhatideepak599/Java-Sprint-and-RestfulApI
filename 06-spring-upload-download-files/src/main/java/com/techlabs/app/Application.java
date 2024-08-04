package com.techlabs.app;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	 @Bean
	    CommandLineRunner init() {
	        return (args) -> {
	            Files.createDirectories(Paths.get("upload-dir"));
	        };
	    }
}
