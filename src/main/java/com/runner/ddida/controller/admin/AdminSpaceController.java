package com.runner.ddida.controller.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.runner.ddida.service.AdminSpaceService;
import com.runner.ddida.vo.SpaceDetailVo;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminSpaceController {
	
	private final AdminSpaceService adminSpaceservice;
	
	@GetMapping("/space")
	public String adminSpaceList(@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize, Model model) {
		List<String> rsrcNoList = adminSpaceservice.getRsrcNoList();
		Map<String, Object> result = adminSpaceservice.show(rsrcNoList, page, pageSize);
		
		model.addAttribute("list", result.get("dataPage"));
		model.addAttribute("currentPage", result.get("currentPage"));
		model.addAttribute("totalPages", result.get("totalPages"));
		return "admin/space/adminSpaceList";
	}

	@GetMapping("/space/search")
	public String postAdminSpaceList(@RequestParam("searchType") String searchType,
			@RequestParam("searchWord") String searchWord, @RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "pageSize", defaultValue = "10") int pageSize, Model model) {
		List<String> rsrcNoList = adminSpaceservice.getRsrcNoList();
		Map<String, Object> result = adminSpaceservice.showFilterdResult(rsrcNoList, searchType, searchWord, page, pageSize);
		
		model.addAttribute("type", searchType);
		model.addAttribute("word", searchWord);
		model.addAttribute("list", result.get("dataPage"));
		model.addAttribute("currentPage", result.get("currentPage"));
		model.addAttribute("totalPages", result.get("totalPages"));

		return "admin/space/adminSpaceList";
	}

	@GetMapping("/space/{rsrcNo}")
	public String adminSpaceDetail(@PathVariable("rsrcNo") String rsrcNo, Model model) {
		SpaceDetailVo result = adminSpaceservice.findDetail(rsrcNo).get(0);
		model.addAttribute("result", result);
		return "admin/space/adminSpaceDetail";
	}

}