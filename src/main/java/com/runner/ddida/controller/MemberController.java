package com.runner.ddida.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.runner.ddida.converter.MemberConverter;
import com.runner.ddida.dto.MemberDto;
import com.runner.ddida.model.Member;
import com.runner.ddida.service.MemberService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

	private final MemberConverter memberConverter;
	private final MemberService memberService;

	@GetMapping("/join")
	public String join() {
		return "join/join";
	}
	
	@GetMapping("/join/admin")
	public String adminJoin() {
		return "join/adminJoin";
	}

	@PostMapping("/join/{role}")
	public String signUp(@ModelAttribute MemberDto memberDto, Model model) {

		Member entity = memberConverter.convertToEntity(memberDto);
		Member savedEntity = memberService.saveMember(entity);
		MemberDto savedDto = memberConverter.convertToDto(savedEntity);

		model.addAttribute("member", savedDto);
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String login() {
		return "login/login";
	}

	@PostMapping("/login")
	public String loginPost(Model model, HttpSession session) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if (authentication != null && authentication.isAuthenticated()) {
	        session.setAttribute("userDetails", authentication.getPrincipal());
	    }
		
		return "redirect:/";
	}

	@GetMapping("/login/admin")
	public String certificate() {
		return "login/adminLogin";
	}
}