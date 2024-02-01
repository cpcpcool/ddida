package com.runner.ddida.service;

import org.springframework.stereotype.Service;

import com.runner.ddida.repository.MypageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MypageService {

	private final MypageRepository mypageRepository;
	
}
