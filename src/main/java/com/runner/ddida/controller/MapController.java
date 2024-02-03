package com.runner.ddida.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.runner.ddida.service.SpaceService;
import com.runner.ddida.vo.SpaceVo;

import lombok.RequiredArgsConstructor;

/**
 * @author 박재용
 * @editDate 24.01.22 ~
 */

@Controller
@RequiredArgsConstructor
public class MapController {

	private final SpaceService spaceService;

	@GetMapping("/ddimap")
	public String ddimap(Model model, Pageable pageable) {
		List<SpaceVo> recmdspcaeList = new ArrayList<>();
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
		
		List<SpaceVo> searchResults = spaceService.searchMapByCriteria(type, pay, region, spaceNm);
		model.addAttribute("data", searchResults);

		return "user/map/spaceMap";
	}

}