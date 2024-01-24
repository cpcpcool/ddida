package com.runner.ddida.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
	public String map() {
		return "user/map/spaceMap";
	}
	
	/*
	 * @ResponseBody
	 * 
	 * @GetMapping("/show") public List<String> hello() { return
	 * Arrays.asList("첫번째 인사", "두번째 인사"); }
	 */
	

}