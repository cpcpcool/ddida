package com.runner.ddida.controller.admin;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.runner.ddida.model.Qna;
import com.runner.ddida.service.QnaService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminQnaController {

	private final QnaService qnaService;

	@GetMapping("/qna")
	public String adminQnaList(
			@PageableDefault(page = 0, size = 10, sort = "qnaNo", direction = Sort.Direction.DESC) Pageable pageable,
			@RequestParam(name = "searchKeyword", required = false) String searchKeyword,
			@RequestParam(name = "searchType", required = false) String searchType, Model model) {
		
		Page<Qna> qnaList = qnaService.searchQna(searchKeyword, searchType, pageable);
		int nowPage = qnaList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, qnaList.getTotalPages());
		int lastPage = qnaList.getTotalPages();

		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("list", qnaList);
		return "admin/qna/adminQnaList";
	}

	@GetMapping("/qna/{qnaNo}")
	public String adminQnaDetail(@PathVariable("qnaNo") Long qnaNo, Model model) {
		Optional<Qna> qnaDetail = qnaService.findByQnaNo(qnaNo);
		model.addAttribute("qnaDetail", qnaDetail.get());
		return "admin/qna/adminQnaDetail";
	}

	@PostMapping("/qna/{qnaNo}")
	public String adminQnaDetailPost(@PathVariable("qnaNo") Long qnaNo, @RequestParam("answer") String answer) {
		qnaService.saveAnswer(qnaNo, answer);
		return "redirect:/admin/qna/" + qnaNo;
	}

}
