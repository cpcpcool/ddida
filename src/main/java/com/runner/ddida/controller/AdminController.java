package com.runner.ddida.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.runner.ddida.service.AdminSpaceService;
import com.runner.ddida.service.SpaceService;
import com.runner.ddida.vo.ApiVo;
import com.runner.ddida.vo.SpaceDetailVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
//@ControllerAdvice(annotations = Controller.class)
@RequestMapping("/admin")
public class AdminController {
	
	private final AdminSpaceService service;
	private final SpaceService spaceService;

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
	
	@GetMapping("/space")
	public String adminSpaceList(@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize, Model model) {
//		List<ApiVo> rsrc = spaceService.findDefault();
//		List<String> rsrcNoList = rsrc.stream().filter(s->s.getRsrcNo()).toList();
		List<String> rsrcNoList = service.getRsrcNoList();
//		Map<String, Object> spaceList = service.findSpaceList(page, pageSize);
		Map<String, Object> result = service.show(rsrcNoList, page, pageSize);
		
		model.addAttribute("list", result.get("dataPage"));
		model.addAttribute("currentPage", result.get("currentPage"));
		model.addAttribute("totalPages", result.get("totalPages"));
		return "admin/space/adminSpaceList";
	}
	
	
	
	
	@GetMapping("/space/search")
	public String postAdminSpaceList(@RequestParam("searchType") String searchType,
			@RequestParam("searchWord") String searchWord, 
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize, Model model) {
		System.out.println("분류선택 : " + searchType);
		System.out.println("검색어 : " + searchWord);
		List<String> rsrcNoList = service.getRsrcNoList();
		Map<String, Object> result = service.showFilterdResult(rsrcNoList, searchType, searchWord, page, pageSize);
		System.out.println("컨트롤러 str : " + result.get("str"));
		System.out.println("컨트롤러 str2 : " + result.get("str2"));
		
		model.addAttribute("list", result.get("dataPage"));
		model.addAttribute("currentPage", result.get("currentPage"));
		model.addAttribute("totalPages", result.get("totalPages"));
		
		return "admin/space/adminSpaceList";
	}
	
	
	
	
	
	@GetMapping("/space/{rsrcNo}")
	public String adminSpaceDetail(@PathVariable("rsrcNo") String rsrcNo, Model model) {
		SpaceDetailVo result = service.findDetail(rsrcNo).get(0);
		model.addAttribute("result", result);
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
