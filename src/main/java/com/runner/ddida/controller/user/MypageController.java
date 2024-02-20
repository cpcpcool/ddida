package com.runner.ddida.controller.user;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.runner.ddida.model.Member;
import com.runner.ddida.model.Reserve;
import com.runner.ddida.service.MemberSignService;
import com.runner.ddida.service.ReserveService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@ControllerAdvice(annotations = Controller.class)
public class MypageController {

	private final MemberSignService memberSignService;
	private final ReserveService reserveService;

	@ModelAttribute("user")
	public UserDetails getCurrentUser(@AuthenticationPrincipal Member member) {
		return member;
	}

	// 예약 내역

	@GetMapping("/mypage/reservation")
	public String reserveList(
			@PageableDefault(page = 0, size = 10, sort = "reserveId", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable,
			@RequestParam(name = "searchKeyword", required = false) String searchKeyword,
			@RequestParam(name = "searchType", required = false) String searchType,
			@AuthenticationPrincipal Member user, Model model) {

		Page<Reserve> reserveList = null;

		if (searchKeyword == null || searchType.isEmpty()) {
			reserveList = reserveService.findAllByUsername(user.getUserNo(), pageable);
		} else if (searchKeyword != null && searchType.equals("rsrcNm")) {
			reserveList = reserveService.findByRsrcNmContaining(user.getUserNo(), searchKeyword, pageable);
		} else if (searchKeyword != null && searchType.equals("useDate")) {
			reserveList = reserveService.findByUseDateContaining(user.getUserNo(), searchKeyword, pageable);
		}

		for (Reserve reserve : reserveList) {
			LocalDate now = LocalDate.now();
			LocalDate useDate = LocalDate.parse(reserve.getUseDate());
			if (now.isAfter(useDate)) {
				reserveService.checkout(reserve.getReserveId());
			}
		}

		int nowPage = reserveList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, reserveList.getTotalPages());

		if (reserveList.getTotalPages() == 0) {
			endPage = 1;
		}

		int lastPage = reserveList.getTotalPages();

		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("lastPage", lastPage);

		model.addAttribute("reserveList", reserveList);

		return "user/mypage/reserveList";
	}

	/*
	 * @GetMapping("/mypage/reservation/list") public ResponseEntity<Page<Reserve>>
	 * getReservationList(
	 * 
	 * @PageableDefault(page = 0, size = 10, sort = "reserveId", direction =
	 * org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable,
	 * 
	 * @RequestParam(name = "searchKeyword", required = false) String searchKeyword,
	 * 
	 * @RequestParam(name = "searchType", required = false) String searchType,
	 * 
	 * @AuthenticationPrincipal Member user, Model model) {
	 * 
	 * Page<Reserve> reserveList = null;
	 * 
	 * if (searchKeyword == null || searchType.isEmpty()) { reserveList =
	 * reserveService.findAllByUsername(user.getUserNo(), pageable); } else if
	 * (searchKeyword != null && searchType.equals("rsrcNm")) { reserveList =
	 * reserveService.findByRsrcNmContaining(user.getUserNo(), searchKeyword,
	 * pageable); } else if (searchKeyword != null && searchType.equals("useDate"))
	 * { reserveList = reserveService.findByUseDateContaining(user.getUserNo(),
	 * searchKeyword, pageable); }
	 * 
	 * for (Reserve reserve : reserveList) { LocalDate now = LocalDate.now();
	 * LocalDate useDate = LocalDate.parse(reserve.getUseDate()); if
	 * (now.isAfter(useDate)) { reserveService.checkout(reserve.getReserveId()); } }
	 * 
	 * int nowPage = reserveList.getPageable().getPageNumber() + 1; int startPage =
	 * Math.max(nowPage - 4, 1); int endPage = Math.min(nowPage + 5,
	 * reserveList.getTotalPages()); int lastPage = reserveList.getTotalPages();
	 * 
	 * model.addAttribute("nowPage", nowPage); model.addAttribute("startPage",
	 * startPage); model.addAttribute("endPage", endPage);
	 * model.addAttribute("lastPage", lastPage);
	 * 
	 * model.addAttribute("reserveList", reserveList);
	 * 
	 * return ResponseEntity.ok().body(reserveList); }
	 */

	// 예약 상세
	@GetMapping("/mypage/reservation/{reserveId}")
	public String reserveDetail(@PathVariable(name = "reserveId") Long reserveId, Model model) {
		Reserve reserveDetail = reserveService.findByReserveId(reserveId).get();

		List<String> useTimeList = reserveService.findUseTimeByReserveId(reserveId);

		model.addAttribute("reserve", reserveDetail);
		model.addAttribute("useTimeList", useTimeList);

		return "user/mypage/reserveDetail";
	}

	// 예약 취소
	@DeleteMapping("/mypage/reservation/{reserveId}")
	public String cancel(@PathVariable(name = "reserveId") Long reserveId) {

		reserveService.cancel(reserveId);

		return "redirect:/mypage/reservation";

	}

	// 후기 등록
	@Transactional
	@PutMapping("/mypage/reservation/review/{reserveId}")
	public String addReview(@PathVariable(name = "reserveId") Long reserveId,
			@ModelAttribute(name = "review") String review) {

		Reserve reserve = reserveService.findByReserveId(reserveId).get();

		reserve.setReview(review);

		return "redirect:/mypage/reservation";
	}

	@GetMapping("/mypage/userInfo")
	public String userInfo() {

		return "user/mypage/userInfo";
	}

	@GetMapping("/mypage/userInfo/edit")
	public String editPassword() {
		return "user/mypage/editPassword";
	}

	// 비밀번호 체크 및 변경
	@PostMapping("/mypage/userInfo/edit")
	public String editPasswordPost(@AuthenticationPrincipal Member member,
			@RequestParam(name = "password") String currentPassword,
			@RequestParam(name = "new-password") String newPassword, RedirectAttributes redirectAttributes) {

		log.info(": {}", currentPassword);
		log.info(": {}", newPassword);

		if (!memberSignService.checkPassword(member.getUsername(), currentPassword)) {
			redirectAttributes.addFlashAttribute("passwordError", "현재 비밀번호가 일치하지 않습니다!");
			return "redirect:/mypage/userInfo/edit";
		} else {
			memberSignService.updatePassword(member.getUsername(), newPassword);
			redirectAttributes.addFlashAttribute("passwordChangeSuccess", "비밀번호가 변경되었습니다. 다시 로그인 해주세요");
			return "redirect:/logout";
		}

	}

}