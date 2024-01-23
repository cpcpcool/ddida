package com.runner.ddida.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class AdminController {

	@GetMapping("/admin/qna")
	public String admintest() {
		return "admin/adminQna";
	}
}
