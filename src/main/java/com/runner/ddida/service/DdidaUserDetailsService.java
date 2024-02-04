package com.runner.ddida.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.runner.ddida.model.Member;
import com.runner.ddida.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

/**
 * @author 박재용
 */

@Service
@RequiredArgsConstructor
public class DdidaUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Member member = memberRepository.findByUsername(username).orElseThrow(()-> 
				new UsernameNotFoundException(username + "없는 회원입니다.")); // 로깅 따로 처리필요 
				

		// DB에서 찾아온 정보 담기
		return Member.builder()
					.userNo(member.getUserNo())
					.username(member.getUsername())
					.password(member.getPassword())
					.role(member.getRole())
					.name(member.getName())
					.phone(member.getPhone())
					.email(member.getEmail())
					.signDate(member.getSignDate())
					.build();
	}

}
