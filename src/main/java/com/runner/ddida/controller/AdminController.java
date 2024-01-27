package com.runner.ddida.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.runner.ddida.service.SpaceService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
//@ControllerAdvice(annotations = Controller.class)
@RequestMapping("/admin")
public class AdminController {
	
	private final SpaceService spaceService;
	
//	@ModelAttribute("ADMIN")
//	public UserDetails getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
//		return userDetails;
//	}
	
	@GetMapping("/qna")
	public String adminQnaList(Model model, HttpSession session) {
		
		session.getAttribute("userDtails");
		
		int[][] arr = new int[10][7];
		for(int i=1; i<10; i++) {
		    for(int j=0; j<7; j++) {
		        arr[i-1][j] = i*(j+1);
		        System.out.printf("%d  ", arr[i-1][j]);
		    }
		    System.out.println("");
		}
		model.addAttribute("arr", arr);
		return "admin/qna/adminQnaList";
	}
	
	@GetMapping("/qna/1")
	public String adminQnaDetail() {
		return "admin/qna/adminQnaDetail";
	}
	
	@GetMapping("/users")
	public String userList(Model model) {
		int[][] arr = new int[10][7];
		for(int i=1; i<10; i++) {
		    for(int j=0; j<7; j++) {
		        arr[i-1][j] = i*(j+1);
		        System.out.printf("%d  ", arr[i-1][j]);
		    }
		    System.out.println("");
		}
		model.addAttribute("arr", arr);
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
	
	@GetMapping("/users/2")
	public String userDetail2(Model model) {
		int[][] arr = new int[3][4];
		for(int i=1; i<3; i++) {
			for(int j=0; j<=3; j++) {
				arr[i-1][j] = i*(j+1);
				System.out.printf("%d  ", arr[i-1][j]);
			}
			System.out.println("");
		}
		model.addAttribute("arr", arr);
		return "admin/users/userDetail2";
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
	
	@GetMapping("/space/detail")
	public String adminFacDetail() {
		return "admin/space/adminSpaceDetail";
	}
	
	@GetMapping("/test")
	public String test() {
		return "admin/space/test";
	}
	
	@GetMapping("/test2")
	public String test2() {
		return "admin/space/test2";
	}
	
}
