package com.runner.ddida.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
	
	@GetMapping("/showss")
    public List<String> hello() {
        return Arrays.asList("첫번째 인사", "두번째 인사");
	}    

}
