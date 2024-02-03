package com.runner.ddida.controller.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.runner.ddida.model.Member;
import com.runner.ddida.model.Qna;
import com.runner.ddida.repository.MemberRepository;
import com.runner.ddida.service.MemberService;
import com.runner.ddida.service.QnaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin")
public class AdminMemberController {

	private final MemberService memberService;
	private final QnaService qnaService;

	@GetMapping("/users")
	public String userList(
			@PageableDefault(page = 0, size = 10, sort = "userNo", direction = Sort.Direction.DESC) Pageable pageable,
			@RequestParam(name = "searchKeyword", required = false) String searchKeyword,
			@RequestParam(name = "searchType", required = false) String searchType, Model model) {
		
		System.out.println("분류 : " + searchType);
		System.out.println("검색어 : " + searchKeyword);
		
		// 검색
		Page<Member> userList = memberService.searchUsers(searchKeyword, searchType, pageable);;
		
		// 페이징
		// page index 0부터 시작
		int nowPage = userList.getPageable().getPageNumber() + 1;
		// 페이지 버튼 최대 10개, -4해서 음수가 나오면 첫 페이지 1
		int startPage = Math.max(nowPage - 4, 1);
		// 마지막 게시글이 존재하는 페이지를 endPage로
		int endPage = Math.min(nowPage + 5, userList.getTotalPages());

		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("userList", userList);

		return "admin/users/userList";
	}

	@GetMapping("/users/{userNo}")
	public String userDetail(@PathVariable(name = "userNo") Long userNo, Model model,
			@PageableDefault(page = 0, size = 10, sort = "userNo", direction = Sort.Direction.DESC) Pageable pageable) {
		Member member = memberService.findByUserNo(userNo).get();
		List<Qna> qnaAll = qnaService.findByUserNo(userNo);
		
		for(Qna q: qnaAll) {
			System.out.println(q.getQnaNo());
			System.out.println(q.getTitle());
			System.out.println(q.getQnaDate());
			System.out.println(q.getDescription());
		}
		model.addAttribute("data", qnaAll);
		model.addAttribute("user", member);
		return "admin/users/userDetail";
	}
	
}
