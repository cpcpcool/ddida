package com.runner.ddida.service;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminMemberController {
	
	private final MemberService memberService;
	
	
	@GetMapping("/users")
	public String userList(Model model) {
		
		return "admin/users/userList";
	}
	
	@GetMapping("/users/{userNo}")
	public String userDetail(Model model) {
		
		
		return "admin/users/userDetail";
	}
	

}
