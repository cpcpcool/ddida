package com.runner.ddida.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.runner.ddida.service.SpaceService;
import com.runner.ddida.vo.SpaceDetailVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 박재용
 * @editDate 24.01.22 ~
 */

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final SpaceService service;

	@GetMapping("/")
	public String main(Model model) {

		Map<String, Object> recmdspcaeList = new HashMap<String, Object>();
//		recmdspcaeList = service.recommendSpaceList();
		recmdspcaeList.get("data");

		model.addAttribute("data", recmdspcaeList.get("data"));

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

	@GetMapping("/mypage/userInfo")
	public String userInfo() {

		return "user/mypage/userInfo";
	}

	@GetMapping("/mypage/userInfo/edit")
	public String editPassword() {

		return "user/mypage/editPassword";
	}

	@GetMapping("/ddimap")
	public String ddimap() {
		return "user/map/spaceMap";
	}

	@GetMapping("/api/ddimap")
	public String api() {
		return "user/map/spaceMap";
	}

	@GetMapping("/sports")
	public String spaceList(Model model) {

		Map<String, Object> spcaeList = new HashMap<String, Object>();
		spcaeList = service.findSpaceList();
		spcaeList.get("dataPage");

		model.addAttribute("data", spcaeList.get("dataPage"));
		model.addAttribute("currentPage", spcaeList.get("page"));
		model.addAttribute("totalPages", spcaeList.get("currentPage"));

		return "user/sports/spaceList";
	}

	@GetMapping("/sports2")
	public String spaceList2(Model model) {

		service.findSpaceDetail();

		model.addAttribute("data", null);

		return "user/sports/spaceList2";
	}

	@GetMapping("/sports/{rsrcNo}")
	public String spaceDetail(@PathVariable("rsrcNo") String rsrcNo, Model model) {

		log.info("이거 맞음");

		SpaceDetailVo data = service.findDetail(rsrcNo).get(0);

		model.addAttribute("data", data);

		return "user/sports/spaceDetail";
	}

	@GetMapping("/sports/1/reserve")
	public String reserveForm() {

		return "user/sports/reserveForm";
	}

	@PostMapping("/sports/1/reserve/check")
	public String checkForm() {

		return "user/sports/checkForm";
	}

	@PostMapping("/sports/1/complete")
	public String complete() {

		return "user/sports/complete";
	}

}