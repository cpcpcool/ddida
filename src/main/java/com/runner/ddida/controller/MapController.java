package com.runner.ddida.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 박재용
 * @editDate 24.01.22 ~
 */

@Controller
@RequiredArgsConstructor
@Slf4j
public class MapController {
	
	@GetMapping("/ddimap")
	public String ddimap(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		model.addAttribute("user", userDetails);
		return "user/map/spaceMap";
	}

	@GetMapping("/api/ddimap")
	public String api() {
		return "user/map/spaceMap";
	}
	
	@GetMapping("ddimap/modal")
	public String modal() {
		return "testjoin";
	}
	
	
	
}