package com.runner.ddida.controller.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.runner.ddida.dto.QnaDto;
import com.runner.ddida.model.Member;
import com.runner.ddida.model.Qna;
import com.runner.ddida.service.QnaService;

import lombok.RequiredArgsConstructor;

/**
 * @author 김민혜
 */

@Controller
@RequiredArgsConstructor
@ControllerAdvice(annotations = Controller.class)
public class QnaController {

	@ModelAttribute("user")
	public UserDetails getCurrentUser(@AuthenticationPrincipal Member member) {
		return member;
	}

	private final QnaService qnaService;

	// 문의 목록
	@GetMapping("/qna")
	public String qnaList(
			@PageableDefault(page = 0, size = 10, sort = "qnaNo", direction = Sort.Direction.DESC) Pageable pageable,
			@RequestParam(name = "searchKeyword", required = false) String searchKeyword,
			@RequestParam(name = "searchType", required = false) String searchType,
			@AuthenticationPrincipal Member user, Model model) {

		Page<Qna> qnaList = null;

		if (searchKeyword == null || searchType.isEmpty()) {
			qnaList = qnaService.findAll(pageable);
		} else if (searchKeyword != null && searchType.equals("title")) {
			qnaList = qnaService.findByTitleContaining(searchKeyword, pageable);
		} else if (searchKeyword != null && searchType.equals("description")) {
			qnaList = qnaService.findByDescriptionContaining(searchKeyword, pageable);
		}

		int nowPage = qnaList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, qnaList.getTotalPages());
		int lastPage = qnaList.getTotalPages();

		model.addAttribute("qnaList", qnaList);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("lastPage", lastPage);
		model.addAttribute("user", user);

		return "user/qna/qnaList";
	}

	// 문의 상세
	@GetMapping("/qna/{qnaNo}")
	public String qnaDetail(@PathVariable(name = "qnaNo") Long qnaNo, @AuthenticationPrincipal Member user,
			Model model) {
		qnaService.viewcnt(qnaNo);
		Qna qna = qnaService.findByQnaNo(qnaNo).get();

		model.addAttribute("qna", qna);
		model.addAttribute("prev", qnaService.prev(qnaNo));
		model.addAttribute("next", qnaService.next(qnaNo));
		model.addAttribute("user", user);

		return "user/qna/qnaDetail";
	}

	// 문의 등록 폼
	@GetMapping("/qna/add")
	public String qnaAddForm() {
		return "user/qna/qnaAddForm";
	}

	// 문의 등록
	@PostMapping("/qna/add")
	public String addQna(QnaDto qnaDto, @AuthenticationPrincipal Member user, Model model) {
		qnaDto.setUsername(user.getUsername());
		qnaDto.setName(user.getName());

		QnaDto qna = qnaService.save(qnaDto);
		model.addAttribute("qna", qna);

		return "redirect:/qna/" + qna.getQnaNo();
	}

	// 문의 수정 폼
	@GetMapping("/qna/editForm/{qnaNo}")
	public String qnaEditForm(@PathVariable(name = "qnaNo") Long qnaNo, Model model) {

		QnaDto qnaDto = qnaService.getQna(qnaNo);

		model.addAttribute("qna", qnaDto);
		return "user/qna/qnaEditForm";
	}

	// 문의 수정
	@PutMapping("/qna/edit/{qnaNo}")
	public String update(QnaDto qnaDto, @PathVariable(name = "qnaNo") Long qnaNo,
			@AuthenticationPrincipal Member user) {

		qnaDto.setUsername(user.getUsername());
		qnaService.update(qnaNo, qnaDto);
		return "redirect:/qna";
	}

	// 문의 삭제
	@DeleteMapping("/qna/{qnaNo}")
	public String deleteQna(@PathVariable(name = "qnaNo") Long qnaNo) {
		qnaService.deleteQna(qnaNo);
		return "redirect:/qna";
	}

}