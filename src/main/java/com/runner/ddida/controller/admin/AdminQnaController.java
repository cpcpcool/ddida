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
import com.runner.ddida.service.SpaceService;
import com.runner.ddida.vo.SpaceDetailVo;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
//@ControllerAdvice(annotations = Controller.class)
@RequestMapping("/admin")
public class AdminQnaController {
	
	private final AdminSpaceService adminSpaceservice;
	private final SpaceService spaceService;
	
	
	@GetMapping("/qna")
	public String adminQnaList(Model model, HttpSession session) {

		session.getAttribute("userDtails");

		int[][] arr = new int[10][7];
		for (int i = 1; i < 10; i++) {
			for (int j = 0; j < 7; j++) {
				arr[i - 1][j] = i * (j + 1);
				System.out.printf("%d  ", arr[i - 1][j]);
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
}
