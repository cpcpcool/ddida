package com.runner.ddida.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminController {

	@GetMapping("/qna")
	public String admintest() {
		return "admin/qna";
	}
	
	@GetMapping("/space")
	public String adminFacList(Model model) {
		int[][] arr = new int[10][5];
		for(int i=1; i<10; i++) {
		    for(int j=0; j<5; j++) {
		        arr[i-1][j] = i*(j+1);
		        System.out.printf("%d  ", arr[i-1][j]);
		    }
		    System.out.println("");
		}
		model.addAttribute("arr", arr);
		return "admin/space/adminSpaceList";
	}
	
	@GetMapping("/test")
	public String test() {
		return "admin/space/test";
	}
	
	@GetMapping("/test2")
	public String test2() {
		return "admin/space/test2";
	}
	
	@GetMapping("/space/detail")
	public String adminFacDetail() {
		return "admin/space/adminSpaceDetail";
	}
	
	@GetMapping("/users")
	public String userList() {
		return "admin/users/userList";
	}
	
	@GetMapping("/users/1")
	public String userDetail(Model model) {
		int[][] arr = new int[3][4];
		for(int i=1; i<3; i++) {
		    for(int j=0; j<=3; j++) {
		        arr[i-1][j] = i*(j+1);
		        System.out.printf("%d  ", arr[i-1][j]);
		    }
		    System.out.println("");
		}
		model.addAttribute("arr", arr);
		return "admin/users/userDetail";
	}
	
}
