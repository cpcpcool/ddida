package com.runner.ddida.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.runner.ddida.dto.MemberDto;
import com.runner.ddida.dto.MemberFormDto;
import com.runner.ddida.model.Member;
import com.runner.ddida.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberSignService {

	private final MemberRepository memberRepository;
	
	private final MemberService memberService;
	
	private final PasswordEncoder passwordEncoder;

	// 일반 회원가입
	public MemberFormDto save(MemberFormDto memberFormDto) {
		
		// 예외처리하기
		String trimmedUsername = memberFormDto.getUsername().replaceAll("\\s", ""); // 공백 제거
		if(!memberService.duplicatedUsername(trimmedUsername)) { 
			throw new IllegalStateException("이미 사용 중인 아이디입니다.");
		}
		
		memberFormDto.encodePassword(passwordEncoder);
		Member member = memberFormDto.toEntity();
		Member savedMember = memberRepository.save(member);

		return savedMember.toMemberFormDto();

	}

	// 관리자 가입
	public MemberFormDto saveAdmin(MemberFormDto memberFormDto) {

		String trimmedUsername = memberFormDto.getUsername().replaceAll("\\s", ""); // 공백 제거
		if(!memberService.duplicatedUsername(trimmedUsername)) {
			throw new IllegalStateException("이미 사용 중인 아이디입니다.");
		}
		
		memberFormDto.encodePassword(passwordEncoder);
		memberFormDto.setRole("ADMIN");
		memberFormDto.setName("-");
		memberFormDto.setPhone("-");
		memberFormDto.setEmail("-");
		Member member = memberFormDto.toEntity(); // 이것만으로 엔티티 객체 생성
		Member savedAdminMember = memberRepository.save(member);

		return savedAdminMember.toMemberFormDto();
	}
	
	// 비밀번호 비교
	public boolean checkPassword(String username, String password) {
		Optional<Member> optionalMember = memberRepository.findByUsername(username);
		return optionalMember.map(member -> passwordEncoder.matches(password, member.getPassword())).orElse(false);
		
	}
	
	// 비밀번호 변경
	@Transactional
	public void updatePassword(String username, String newPassword) {
		Optional<Member> optionalMember = memberRepository.findByUsername(username);
		MemberFormDto memberFormDto = optionalMember.get().toMemberFormDto();
		memberFormDto.setPassword(newPassword);
		memberFormDto.encodePassword(passwordEncoder);
		// 변경 저장
		memberRepository.save(memberFormDto.toEntity());
	}

}