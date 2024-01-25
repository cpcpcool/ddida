package com.runner.ddida.controller;

import java.util.HashMap;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
	
	@GetMapping("/api/hello")			
	public HashMap<String, String> hello() {
		HashMap<String, String> result = new HashMap<String, String>();
		result.put("hello", "안녕하세요");
		
		return result;
	}

	@GetMapping("/test/hello")
	public String helloRuckus(Model model) {
		return "Hello Ruckus";
	}
}
