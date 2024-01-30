package com.runner.ddida.controller;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.runner.ddida.service.SpaceService;
import com.runner.ddida.vo.ApiVo;
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
public class MapController {

	private final SpaceService spaceService;

	@GetMapping("/ddimap")
	public String ddimap(Model model) {
		List<ApiVo> recmdspcaeList = new ArrayList<>();
		recmdspcaeList = spaceService.recommendSpaceList();
//		recmdspcaeList = spaceService.findDefault();
		model.addAttribute("data", recmdspcaeList);

		return "user/map/spaceMap";
	}

	@GetMapping("/ddimap/search")
	public String searchData(Model medel, @RequestParam(name = "type", required = false) String type,
										@RequestParam(name = "pay", required = false) String pay,
										@RequestParam(name = "region", required = false) String region,
										@RequestParam(name = "spaceNm", required = false) String spaceNm) {
		
		 // 동적으로 검색 쿼리를 생성하는 비즈니스 로직 호출
		System.out.println("type: " + type);
		System.out.println("region: " + region);
		System.out.println("spaceNm: " + spaceNm);
		
        List<ApiVo> searchResults = spaceService.searchByCriteria(type, pay, region, spaceNm);
        medel.addAttribute("data", searchResults);
        
//        if(pay.equals("N")) {
//			List<ApiVo> searchList = spaceService.findDefault();
//
//			List<SpaceDetailVo> detailList = spaceService.findDetailList(searchList);
//			// 무료/유료 데이터 반환 성공
//			List<ApiVo> filteredList = spaceService.findDefault();
//			
//			filteredList = searchList.stream()
//					.filter(apiVo -> detailList.stream().anyMatch(
//							detail -> apiVo.getRsrcNo().equals(detail.getRsrcNo()) && "N".equals(detail.getFreeYn())))
//					.collect(Collectors.toList());
//			
//			medel.addAttribute("data", filteredList);
		return "user/map/spaceMap";
	}

}