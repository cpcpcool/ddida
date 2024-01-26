package com.runner.ddida.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {


	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/join")
	public String join() {
		return "join";
	}

	@GetMapping("/admin")
	public String certificate() {
		return "certificate";
	}
	
}