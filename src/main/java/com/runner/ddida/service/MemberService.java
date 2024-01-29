package com.runner.ddida.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.runner.ddida.model.Member;
import com.runner.ddida.repository.MemberRepository;
import com.runner.ddida.security.MemberPrincipalDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService implements UserDetailsService {
	
	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//넘겨받은 아이디로 DB에서 회원정보 찾음
		Member member = memberRepository.findByUsername(username);	
		
		log.info("username : {}", username);
		log.info("member: {}", member);
		
		//사용자 x
		if(member == null) {
			throw new UsernameNotFoundException(username+"는 없는 계정입니다.");
		}
		
		//사용자 o
		// 커스텀한 구현체에 member넘겨줌
		return new MemberPrincipalDetails(member);
	}
	
	public Member saveMember(Member member) {
		//password 암호화		
		member.encodePassword(passwordEncoder);
		return memberRepository.save(member);
	}
	
}