package com.runner.ddida.controller.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.runner.ddida.model.Member;
import com.runner.ddida.service.SpaceService;
import com.runner.ddida.vo.SpaceVo;

import lombok.RequiredArgsConstructor;

/**
 * @author 박재용
 * @editDate 24.01.22 ~
 */

@Controller
@RequiredArgsConstructor
public class MainController {

	private final SpaceService spaceService;

	@GetMapping("/")
	public String main(Model model, @AuthenticationPrincipal Member member) {
		// 추천 시설
		List<SpaceVo> recmdspaceList = new ArrayList<>();
		recmdspaceList = spaceService.recommendSpaceList();
		model.addAttribute("data", recmdspaceList);
		model.addAttribute("user", member);
		
		return "index";
	}
		

}