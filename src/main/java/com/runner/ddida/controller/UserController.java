package com.runner.ddida.controller;

import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;

/**
 * @author 박재용
 * @editDate 24.01.22 ~
 */

@Controller
@RequiredArgsConstructor
public class UserController {

	/**
	 * @author 박재용
	 * @editDate 24.01.22 ~
	 */

	@GetMapping("/")
	public String main() {
		return "index";
	}

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
	
	@GetMapping("/ddimap")
	public String ddimap() {
		return "user/map/spaceMap";
	}
	
	@GetMapping("/api/ddimap")
	public String api() {
		return "user/map/spaceMap";
	}
	
	
	
	
	
}