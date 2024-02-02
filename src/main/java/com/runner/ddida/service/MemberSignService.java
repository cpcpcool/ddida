package com.runner.ddida.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.runner.ddida.dto.MemberFormDto;
import com.runner.ddida.model.Member;
import com.runner.ddida.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberSignService {

	private final MemberRepository memberRepository;

	private final PasswordEncoder passwordEncoder;

	// 중복검사 메서드
	public boolean duplicatedUsername(String username) {
		return !memberRepository.existsByUsername(username);
	}

	// 일반 회원가입
	public MemberFormDto save(MemberFormDto memberFormDto) {

		// 예외처리하기
		String trimmedUsername = memberFormDto.getUsername().replaceAll("\\s", ""); // 공백 제거
		if (!duplicatedUsername(trimmedUsername)) {
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
		if (!duplicatedUsername(trimmedUsername)) {
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
	@Modifying
	public void updatePassword(String username, String newPassword) {
//		Optional<Member> optionalMember = memberRepository.findByUsername(username);
		Member member = memberRepository.findByUsername(username).get();

		log.info(newPassword);
		String newCryptPassword = passwordEncoder.encode(newPassword);
		log.info(newCryptPassword);
		member.setPassword(newCryptPassword);

		// 비밀번호만 업데이트
		memberRepository.save(member);
	}

}