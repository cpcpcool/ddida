package com.runner.ddida.controller;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
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
		recmdspcaeList = spaceService.findDefault();
		model.addAttribute("data", recmdspcaeList);

		return "user/map/spaceMap";
	}

	@GetMapping("/ddimap/search")
	public String searchData(Model model, @RequestParam(name = "type", required = true) String type,
			@RequestParam(name = "pay", required = false) String pay,
			@RequestParam(name = "region", required = false) String region,
			@RequestParam(name = "spaceNm", required = false) String spaceNm) {

		model.addAttribute("type", type);
		model.addAttribute("pay", pay);
		model.addAttribute("region", region);
		model.addAttribute("spaceNm", spaceNm);
		
		List<ApiVo> searchResults = spaceService.searchByCriteria(type, pay, region, spaceNm);
		model.addAttribute("data", searchResults);

		return "user/map/spaceMap";
	}

}