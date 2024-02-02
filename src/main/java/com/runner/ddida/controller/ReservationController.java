package com.runner.ddida.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.runner.ddida.service.SpaceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationController {
	
	private final SpaceService spaceService;

    @GetMapping("/availableTimes")
    public List<String> getAvailableTimes(@RequestParam("date") String date, @RequestParam("rsrcNo") String rsrcNo) {
    	
        return spaceService.getAvailableTimes(date, rsrcNo);
    }

}