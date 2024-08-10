package com.techlabs.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
	
	@GetMapping("/hlo")
	public String hlo() {
		return "Hello";
	}
}
