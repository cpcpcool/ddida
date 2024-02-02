package com.runner.ddida.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
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
import com.runner.ddida.model.ReserveTime;
import com.runner.ddida.repository.ReserveRepository;
import com.runner.ddida.service.MemberSignService;
import com.runner.ddida.service.QnaService;
import com.runner.ddida.service.SpaceService;
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
	public String qnaDetail(@PathVariable(name = "qnaNo") Long qnaNo,
			@AuthenticationPrincipal Member user, Model model) {
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
	public String spaceList(@AuthenticationPrincipal UserDetails userDetails, Model model,
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "pageSize", defaultValue = "12") int pageSize) {

		model.addAttribute("user", userDetails);
		Map<String, Object> spcaeList = new HashMap<String, Object>();
		spcaeList = spaceService.findSpaceList(page, pageSize);

		model.addAttribute("data", spcaeList.get("dataPage"));
		model.addAttribute("currentPage", spcaeList.get("currentPage"));
		model.addAttribute("totalPages", spcaeList.get("totalPages"));

		return "user/sports/spaceList";
	}

	@GetMapping("/sports/search")
	public String spaceSearch(Model model, @RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "pageSize", defaultValue = "12") int pageSize,
			@RequestParam(name = "sports") String sportsNm) {

		switch (sportsNm) {
		case "soccer":
			sportsNm = "축구장";
			break;
		case "futsal":
			sportsNm = "풋살장";
			break;
		case "tennis":
			sportsNm = "테니스장";
			break;
		case "badminton":
			sportsNm = "배드민턴장";
			break;
		}

		List<String> apiVoIdList = spaceService.findApiVoIdList(page, pageSize);
		ArrayList<SpaceDetailVo> searchSportsList = new ArrayList<>();

		int batchSize = 100;
		int totalIterations = 10;

		for (int j = 0; j < totalIterations; j++) {
			List<String> searchList = new ArrayList<>();
			int startIdx = j * batchSize;
			int endIdx = Math.min((j + 1) * batchSize, apiVoIdList.size());
			for (int i = startIdx; i < endIdx; i++) {
				searchList.add(apiVoIdList.get(i));
			}
			searchSportsList.addAll(spaceService.findSports(searchList, sportsNm));
		}

		model.addAttribute("data", searchSportsList);

		return "user/sports/spaceList";
	}

	/*
	 * @GetMapping("/sports/search") public String spaceSearch(Model model,
	 * 
	 * @RequestParam(name = "page", defaultValue = "1") int page,
	 * 
	 * @RequestParam(name = "pageSize", defaultValue = "12") int pageSize,
	 * 
	 * @RequestParam(name = "search") String search) {
	 * 
	 * Map<String, Object> spcaeList = new HashMap<String, Object>(); spcaeList =
	 * service.findSeachList(page, pageSize, search);
	 * 
	 * System.out.println(spcaeList.get("dataPage"));
	 * 
	 * model.addAttribute("search", search); model.addAttribute("data",
	 * spcaeList.get("dataPage")); model.addAttribute("currentPage",
	 * spcaeList.get("currentPage")); model.addAttribute("totalPages",
	 * spcaeList.get("totalPages"));
	 * 
	 * return "user/sports/spaceList"; }
	 */

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
		
		List<List<String>> timeSlots = Arrays.asList(
		        Arrays.asList("06:00 ~ 07:00", "07:00 ~ 08:00"),
		        Arrays.asList("08:00 ~ 09:00", "09:00 ~ 10:00"),
		        Arrays.asList("10:00 ~ 11:00", "11:00 ~ 12:00"),
		        Arrays.asList("12:00 ~ 13:00", "13:00 ~ 14:00"),
		        Arrays.asList("14:00 ~ 15:00", "15:00 ~ 16:00"),
		        Arrays.asList("16:00 ~ 17:00", "17:00 ~ 18:00"),
		        Arrays.asList("18:00 ~ 19:00", "19:00 ~ 20:00"),
		        Arrays.asList("20:00 ~ 21:00", "21:00 ~ 22:00"),
		        Arrays.asList("22:00 ~ 23:00", "23:00 ~ 24:00")
		);
		
        model.addAttribute("timeSlots", timeSlots);
		
		
		List<Reserve> reserveL = spaceService.findByRsrcNo(rsrcNo);

		List<Long> reserveId = new ArrayList<Long>();
		
		for (Reserve reserve : reserveL) {
			Long id = reserve.getReserveId();
			reserveId.add(id);
		}
		
		model.addAttribute("reserveId", reserveId);
		model.addAttribute("data", data);
		model.addAttribute("rsrcNo", rsrcNo);

		return "user/sports/reserveForm";
	}
	
	@PostMapping("/sports/complete")
	public String complete(@ModelAttribute Reserve reserve, @RequestParam("useTime") String useTime, RedirectAttributes redirectAttributes) {
		
		String useTimes = useTime.replace("\n", "<br>");
		
		List<ReserveTime> reserveTimeList = new ArrayList<ReserveTime>();
		String[] timeArray = useTime.split("\n");

		for (String time : timeArray) {
			ReserveTime reserveTime = new ReserveTime();
			reserveTime.setUseTime(time);
			reserveTimeList.add(reserveTime);
		}
		reserve.setReserveTimes(reserveTimeList);
		spaceService.saveReserve(reserve);

		redirectAttributes.addFlashAttribute("data", reserve);
		redirectAttributes.addFlashAttribute("useTimes", useTimes);

		return "redirect:/user/sports/complete";
	}

	@GetMapping("/user/sports/complete")
	public String complete(@ModelAttribute("data") Reserve reserve, @ModelAttribute("useTimes") String useTimes, Model model) {
		
		if(useTimes.isEmpty()) {
			return "redirect:/";
		}
		
	    model.addAttribute("data", reserve);
	    model.addAttribute("useTimes", useTimes);

	    return "user/sports/complete";
	}
	
}