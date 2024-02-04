package com.runner.ddida.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.runner.ddida.dto.MemberFormDto;
import com.runner.ddida.service.MemberSignService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * @author 박재용
 */

@Controller
@RequiredArgsConstructor
public class MemberController {

	private final MemberSignService memberSignService;

	@GetMapping("/join")
	public String join(Model model) {
		model.addAttribute("memberFormDto", new MemberFormDto());
		return "user/join";
	}

	@PostMapping("/join/{role}")
	public String signUp(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("memberFormDto", memberFormDto);
			return "user/join";
		}

		try {
			MemberFormDto savedmemberDto = memberSignService.save(memberFormDto);
			model.addAttribute("user", savedmemberDto);
			return "redirect:/success";
			
		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "user/join";
		}
	}

	@GetMapping("/newAdmin")
	public String joinAdmin(Model model) {
		model.addAttribute("userForm", new MemberFormDto());
		return "admin/adminJoin";
	}

	@PostMapping("/newAdmin")
	public String joinAdminPost(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("memberFormDto", memberFormDto);
			return "admin/adminJoin";
		}
		try {
			MemberFormDto savedmemberDto = memberSignService.saveAdmin(memberFormDto);
			model.addAttribute("user", savedmemberDto);
			return "redirect:/success";

		} catch (IllegalStateException e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "admin/adminJoin";
		}

	}
	
	@GetMapping("/success")
	public String success(Model model) {
		return "pop/success";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/access/denied")
	public String loginFail() {
		
		return "pop/administrator";
	}
	
}