package com.runner.ddida.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.runner.ddida.dto.MemberFormDto;
import com.runner.ddida.model.Member;
import com.runner.ddida.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberSignService {

	private final MemberRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public MemberFormDto save(MemberFormDto memberFormDto) {
		
		// 비밀번호 암호화 
		memberFormDto.encodePassword(passwordEncoder); 
		// DTO to Entity
		Member member = memberFormDto.toEntity(); // 이것만으로 엔티티 객체 생성
		// save (DB 저장)
		Member savedMember = userRepository.save(member);
		
		return savedMember.toMemberFormDto();
		
	}
	
}
