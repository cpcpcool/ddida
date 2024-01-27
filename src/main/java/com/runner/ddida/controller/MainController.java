package com.runner.ddida.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.runner.ddida.dto.MemberDto;
import com.runner.ddida.security.MemberPrincipalDetails;
import com.runner.ddida.service.MemberService;
import com.runner.ddida.service.SpaceService;

import lombok.RequiredArgsConstructor;

/**
 * @author 박재용
 * @editDate 24.01.22 ~
 */

@Controller
@RequiredArgsConstructor
public class MainController {

	private final SpaceService spaceService;
	private final MemberService memberService;

	@GetMapping("/")
	public String main(Model model) {

		// 추천 시설
		Map<String, Object> recmdspcaeList = new HashMap<String, Object>();
		recmdspcaeList = spaceService.recommendSpaceList();
		recmdspcaeList.get("data");

		model.addAttribute("data", recmdspcaeList.get("data"));

		// 로그인 정보
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()
				&& !(authentication instanceof AnonymousAuthenticationToken)) {
			MemberPrincipalDetails principal = (MemberPrincipalDetails) memberService
					.loadUserByUsername(authentication.getName());
			MemberDto userDetails = principal.toMemberDto();
			model.addAttribute("user", userDetails);
		}

		return "index";
	}
}