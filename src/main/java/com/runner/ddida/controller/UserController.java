package com.runner.ddida.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.runner.ddida.dto.QnaDto;
import com.runner.ddida.model.Member;
import com.runner.ddida.model.Qna;
import com.runner.ddida.model.Reserve;
import com.runner.ddida.service.MemberSignService;
import com.runner.ddida.service.QnaService;
import com.runner.ddida.dto.ReserveDto;
import com.runner.ddida.model.Reserve;
import com.runner.ddida.model.ReserveTime;
import com.runner.ddida.service.SpaceService;
import com.runner.ddida.vo.ApiVo;
import com.runner.ddida.vo.SpaceDetailVo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 박재용
 */

@Controller
@RequiredArgsConstructor
@Slf4j
@ControllerAdvice(annotations = Controller.class)
public class UserController {

	@ModelAttribute("user")
	public UserDetails getCurrentUser(@AuthenticationPrincipal Member member) {
		return member;
	}

	private final SpaceService spaceService;
	private final MemberSignService memberSignService;
	private final QnaService qnaService;

	// 문의 목록
	@GetMapping("/qna")
	public String qnaList(
			@PageableDefault(page = 0, size = 10, sort = "qnaNo", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable,
			@RequestParam(name = "searchKeyword", required = false) String searchKeyword,
			@RequestParam(name = "searchType", required = false) String searchType, Model model) {

		Page<Qna> qnaList = null;

		if (searchKeyword == null || searchType == null) {
			qnaList = qnaService.findAll(pageable);
		} else if (searchKeyword != null && searchType.equals("title")) {
			qnaList = qnaService.findByTitleContaining(searchKeyword, pageable);
		} else if (searchKeyword != null && searchType.equals("description")) {
			qnaList = qnaService.findByDescriptionContaining(searchKeyword, pageable);
		}

		int nowPage = qnaList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage - 4, 1);
		int endPage = Math.min(nowPage + 5, qnaList.getTotalPages());

		model.addAttribute("qnaList", qnaList);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

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
		qnaDto.setUserName(user.getUsername());

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
	public String update(QnaDto qnaDto, @AuthenticationPrincipal Member user) {
		qnaDto.setUserName(user.getUsername());
		qnaDto.setQnaView(qnaDto.getQnaView());
		qnaService.save(qnaDto);
		return "redirect:/qna";
	}

	// 문의 삭제
	@DeleteMapping("/qna/{qnaNo}")
	public String deleteQna(@PathVariable(name = "qnaNo") Long qnaNo) {
		qnaService.deleteQna(qnaNo);
		return "redirect:/qna";
	}

	// 예약 내역 목록
	@GetMapping("/mypage/reservation")
	public String reserveList(Model model) {
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

	@PostMapping("/mypage/userInfo/edit")
	public String editPasswordPost(@AuthenticationPrincipal Member member,
			@RequestParam(name = "password") String currentPassword,
			@RequestParam(name = "new-password") String newPassword, RedirectAttributes redirectAttributes) {

		log.info(": {}", currentPassword);
		log.info(": {}", newPassword);

		// 비밀번호 체크 및 변경
		if (!memberSignService.checkPassword(member.getUsername(), currentPassword)) {
			redirectAttributes.addFlashAttribute("passwordError", "현재 비밀번호가 일치하지 않습니다!");
			return "redirect:/mypage/userInfo/edit";
		} else {
			// 비밀번호 변경
			memberSignService.updatePassword(member.getUsername(), newPassword);
			redirectAttributes.addFlashAttribute("passwordChangeSuccess", "비밀번호가 변경되었습니다. 다시 로그인 해주세요");
			return "redirect:/logout";
		}

	}

	// ==================================================================================

	@GetMapping("/sports")
	public String spaceList(Model model, @PageableDefault(page = 0, size = 12) Pageable pageable) {

int nowPage = pageable.getPageNumber() + 1;
		
		// 고정 페이지 크기
		int fixedPageSize = 10;
		
		// 시작 페이지 계산
		int startPage = (nowPage - 1) / fixedPageSize * fixedPageSize + 1;
		
		// 종료 페이지 계산
		int endPage = startPage + fixedPageSize - 1;
		PageRequest fixedPageable = PageRequest.of(nowPage - 1, fixedPageSize, pageable.getSort());

		Page<ApiVo> spaceList = spaceService.findSpaceList(fixedPageable);

		model.addAttribute("data", spaceList);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		return "user/sports/spaceList";
	}

	@GetMapping("/sports/search")
	public String spaceSearch(Model model, @PageableDefault(page = 0, size = 12) Pageable pageable,
			@RequestParam(name = "type", required = true) String type,
			@RequestParam(name = "pay", required = false) String pay,
			@RequestParam(name = "region", required = false) String region,
			@RequestParam(name = "spaceNm", required = false) String spaceNm) {
		
		int nowPage = pageable.getPageNumber() + 1;
		
		// 고정 페이지 크기
		int fixedPageSize = 10;
		
		// 시작 페이지 계산
		int startPage = (nowPage - 1) / fixedPageSize * fixedPageSize + 1;
		
		// 종료 페이지 계산
		int endPage = startPage + fixedPageSize - 1;
		PageRequest fixedPageable = PageRequest.of(nowPage - 1, fixedPageSize, pageable.getSort());

		Page<ApiVo> searchSpaceList = spaceService.searchMainByCriteria(type, pay, region, spaceNm, fixedPageable);

		model.addAttribute("data", searchSpaceList);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "user/sports/spaceList";
	}

	@GetMapping("/sports/{rsrcNo}")
	public String spaceDetail(@PathVariable("rsrcNo") String rsrcNo, Model model) {

		log.info("이거 맞음");

		SpaceDetailVo data = spaceService.findDetail(rsrcNo).get(0);

		System.out.println(data.getAmt1());
		System.out.println(data.getAmt2());
		System.out.println(data.getBnrImgFileUrl());
		System.out.println(data.getBnrImgFileUrlAddr());

		model.addAttribute("data", data);

		return "user/sports/spaceDetail";
	}

	@GetMapping("/sports/{rsrcNo}/reserve")
	public String reserveForm(@PathVariable("rsrcNo") String rsrcNo, Model model) {
		SpaceDetailVo data = spaceService.findDetail(rsrcNo).get(0);

		List<Reserve> reserveL = spaceService.findReserve();

		List<String> useDate = new ArrayList<String>();

		for (Reserve reserve : reserveL) {
			if (rsrcNo.equals(reserve.getRsrcNo())) {
				String date = reserve.getUseDate();
				useDate.add(date);
			}
		}
		model.addAttribute("reserve", useDate);

		model.addAttribute("data", data);
		model.addAttribute("rsrcNo", rsrcNo);

		return "user/sports/reserveForm";
	}

	@PostMapping("/sports/{rsrcNo}/reserve/check")
	public String checkForm(@ModelAttribute ReserveDto reserveDto, Model model) {

		String newString = reserveDto.getUseStartTime();

		reserveDto.setUseStartTime(newString.replace("\n", "<br>"));

		String phone = reserveDto.getUserPhoneOne() + "-" + reserveDto.getUserPhoneTwo() + "-"
				+ reserveDto.getUserPhoneThr();
		String email = reserveDto.getUserEmailOne() + "@" + reserveDto.getUserEmailTwo();

		Reserve reserve = new Reserve();

		reserve.setUserNo(reserveDto.getUserNo());
		reserve.setRsrcNo(reserveDto.getRsrcNo());
		reserve.setSpaceName(reserveDto.getRsrcNm());
		reserve.setUseDate(reserveDto.getUseStartDate());
		reserve.setReserveFee(reserveDto.getReserveFee());
		reserve.setEmail(email);
		reserve.setPhone(phone);
		reserve.setName(reserveDto.getUserName());

		model.addAttribute("data", reserve);
		model.addAttribute("time", reserveDto);
		model.addAttribute("timeList", newString);

		return "user/sports/checkForm";
	}

	@PostMapping("/sports/complete")
	public String complete(@ModelAttribute Reserve reserve, @RequestParam("timeList") String timeList, Model model) {

		List<ReserveTime> reserveTimeList = new ArrayList<ReserveTime>();
		String[] timeArray = timeList.split("\n");

		for (String time : timeArray) {
			ReserveTime reserveTime = new ReserveTime();
			reserveTime.setUseTime(time);
			reserveTimeList.add(reserveTime);
		}
		reserve.setReserveTimes(reserveTimeList);

		spaceService.saveReserve(reserve);

		model.addAttribute("data", reserve);
		model.addAttribute("time", timeList.replace("\n", "<br>"));

		return "user/sports/complete";
	}

}