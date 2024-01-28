package com.runner.ddida.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

	@GetMapping("/")
	public String main(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		// 추천 시설
		Map<String, Object> recmdspcaeList = new HashMap<String, Object>();
		recmdspcaeList = spaceService.recommendSpaceList();
		Object recmdData = recmdspcaeList.get("data");

		model.addAttribute("data", recmdData);

		// 로그인 정보
		model.addAttribute("user", userDetails);
		// 로그인 정보(불필요)
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//		if (authentication != null && authentication.isAuthenticated()
//				&& !(authentication instanceof AnonymousAuthenticationToken)) {
//			MemberPrincipalDetails principal = (MemberPrincipalDetails) memberService
//					.loadUserByUsername(authentication.getName());
//			MemberDto memberDto = principal.toMemberDto();
//			model.addAttribute("user", memberDto);
//	}
		
		return "index";
	}
		

}