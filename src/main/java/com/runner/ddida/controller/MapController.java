package com.runner.ddida.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.runner.ddida.service.SpaceService;

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
		// 시설 정보
		Map<String, Object> recmdspcaeList = new HashMap<String, Object>();
		recmdspcaeList = spaceService.recommendSpaceList();
		Object recmdData = recmdspcaeList.get("data");

		model.addAttribute("data", recmdData);

		return "user/map/spaceMap";
	}

	@GetMapping("/api/ddimap")
	public String api() {
		return "user/map/spaceMap";
	}

	@GetMapping("ddimap/modal")
	public String modal() {
		return "testjoin";
	}

}