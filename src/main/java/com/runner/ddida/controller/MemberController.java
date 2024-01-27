package com.runner.ddida.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
		return "join";
	}

	@GetMapping("/join/manage")
	public String adminJoin() {
		return "adminJoin";
	}

	@PostMapping("/join/{role}")
	public String signUp(@ModelAttribute MemberDto memberDto, @PathVariable(value = "role") String role, Model model) {
		
		log.info("role : {}", role);
		
		if("ADMIN".equals(role)) {
			memberDto.setRole(role);
			Member entity = memberConverter.convertToEntity(memberDto);
			Member savedEntity = memberService.saveMember(entity);
			MemberDto savedDto = memberConverter.convertToDto(savedEntity);
			model.addAttribute("user", savedDto);
			return "adminLogin";
		}else {
			memberDto.setRole(role);
			Member entity = memberConverter.convertToEntity(memberDto);
			Member savedEntity = memberService.saveMember(entity);
			MemberDto savedDto = memberConverter.convertToDto(savedEntity);
			model.addAttribute("user", savedDto);
			return "login";
		}
		
	}
	
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

	@GetMapping("/login/manage")
	public String adminLogin() {
		return "adminLogin";
	}

//	@PostMapping("/login/manage")
//	public String adminLoginPost(Model model, HttpSession session) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//		if (authentication != null && authentication.isAuthenticated()) {
//			session.setAttribute("userDetails", authentication.getPrincipal());
//		}
//
//		return "redirect:/admin/qna";
//	}
}