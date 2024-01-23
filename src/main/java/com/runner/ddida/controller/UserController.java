package com.runner.ddida.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.RequiredArgsConstructor;

/**
* @author 박재용
* @editDate 24.01.22 ~
*/

@Controller
@RequiredArgsConstructor
public class UserController {

	@GetMapping("/")
	public String main() {

		return "index";
	}
	
	@GetMapping("/sports")
	public String spaceList() {

		return "user/sports/spaceList";
	}
	
	@GetMapping("/sports/1")
	public String spaceDetail() {

		return "user/sports/spaceDetail";
	}
	
	@GetMapping("/sports/1/reserve")
	public String reserveForm() {

		return "user/sports/reserveForm";
	}

}