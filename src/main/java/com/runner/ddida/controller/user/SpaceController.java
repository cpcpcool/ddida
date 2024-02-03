package com.runner.ddida.controller.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.runner.ddida.model.Member;
import com.runner.ddida.model.Reserve;
import com.runner.ddida.model.ReserveTime;
import com.runner.ddida.service.SpaceService;
import com.runner.ddida.vo.SpaceDetailVo;
import com.runner.ddida.vo.SpaceVo;

import lombok.RequiredArgsConstructor;

/**
 * @author 박재용
 */

@Controller
@RequiredArgsConstructor
@ControllerAdvice(annotations = Controller.class)
public class SpaceController {
	
	@ModelAttribute("user")
	public UserDetails getCurrentUser(@AuthenticationPrincipal Member member) {
		return member;
	}

	private final SpaceService spaceService;

	@GetMapping("/sports")
	public String spaceList(Model model, @PageableDefault(page = 0, size = 12) Pageable pageable) {
		
		Page<SpaceVo> spaceList = spaceService.findSpaceList(pageable);
		 	
		// Open-api 개수제한 이슈로 보류
//		List<SpaceDetailVo> freeYnList = spaceService.findDetailList(spaceService.findDefault());
//		Map<String,String> free = new HashMap<String, String>();
//		for(SpaceDetailVo fre : freeYnList) {
//			free.put(fre.getRsrcNo(),fre.getFreeYn());
//		}
//		model.addAttribute("free", free);
		
		int nowPage = spaceList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage, 1);
		int endPage = Math.min(nowPage + 9, spaceList.getTotalPages());

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
		
		Page<SpaceVo> searchSpaceList = spaceService.searchMainByCriteria(type, pay, region, spaceNm, pageable);
		model.addAttribute("type", type);
		model.addAttribute("pay", pay);
		model.addAttribute("spaceNm", spaceNm);
		

		int nowPage = searchSpaceList.getPageable().getPageNumber() + 1;
		int startPage = Math.max(nowPage, 1);
		int endPage = Math.min(nowPage + 9, searchSpaceList.getTotalPages());

		model.addAttribute("data", searchSpaceList);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);

		return "user/sports/spaceList";
	}

	@GetMapping("/sports/{rsrcNo}")
	public String spaceDetail(@PathVariable("rsrcNo") String rsrcNo, Model model) {
		SpaceDetailVo data = spaceService.findDetail(rsrcNo).get(0);

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
		// model.addAttribute("date", useDate);

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