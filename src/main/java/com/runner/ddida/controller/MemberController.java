package com.runner.ddida.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.runner.ddida.dto.MemberFormDto;
import com.runner.ddida.service.MemberSignService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

	private final MemberSignService memberSignService;
	
//	@GetMapping("/join/admin")
//	public String joinAdmin(Model model) {
//		model.addAttribute("userForm", new MemberFormDto());
//		return "adminJoingvf";
//	}
	

	@GetMapping("/login")
	public String login() {
		return "login";
	}

//	@PostMapping("/login")
//	public String loginPost(Model model, HttpSession session) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//		if (authentication != null && authentication.isAuthenticated()) {
//			session.setAttribute("userDetails", authentication.getPrincipal());
//		}
//
//		return "redirect:/";
//	}

	@GetMapping("/login/admin")
	public String adminLogin() {
		return "adminLogin";
	}

//	@PostMapping("/login/admin")
//	public String adminLoginPost(Model model, HttpSession session) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//		if (authentication != null && authentication.isAuthenticated()) {
//			session.setAttribute("userDetails", authentication.getPrincipal());
//		}
//
//		return "redirect:/admin/qna";
//	}
	
	@GetMapping("/join")
	public String join(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "join";
	}
	
	@PostMapping("/join/{role}")
	public String signUp(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
	        model.addAttribute("memberFormDto", memberFormDto);
			return "join";
		}
	
		MemberFormDto savedmemberDto = memberSignService.save(memberFormDto);
		model.addAttribute("user", savedmemberDto);
		
		return "login";
	}
}