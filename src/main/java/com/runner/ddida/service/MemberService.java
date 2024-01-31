package com.runner.ddida.service;

import org.springframework.stereotype.Service;

import com.runner.ddida.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	// 중복검사 메서드
	public boolean duplicatedUsername(String username) {
		return !memberRepository.existsByUsername(username);
	}
}
