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

	@GetMapping("/")
	public String main() {

		return "index";
	}
	
	
	@GetMapping("/qna")
	public String qnaList() {
		
		return "user/qna/qnaList";
	}
	
	@GetMapping("/qna/qnaDetail")
	public String qnaDetail() {
		
		return "user/qna/qnaDetail";
	}
	
	@GetMapping("/qna/add")
	public String qnaAddForm() {
		
		return "user/qna/qnaAddForm";
	}
	
	@GetMapping("/mypage/reservation")
	public String reserveList() {
		
		return "user/mypage/reserveList";
	}
	
	@GetMapping("/mypage/reservation/reserveDetail")
	public String reserveDetail() {
		
		return "user/mypage/reserveDetail";
	}

}