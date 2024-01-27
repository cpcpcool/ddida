package com.runner.ddida.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.runner.ddida.dto.MemberDto;
import com.runner.ddida.model.Member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Spring Security 에 있는 UserDetails 를 구현한 클래스,
//이 클래스를 통해 Spring Security 에서 사용자의 정보를 담아둠

@Setter
@Getter
@Component
@NoArgsConstructor
public class MemberPrincipalDetails implements UserDetails {

	/**
	 * @author 박재용
	 */

	private static final long serialVersionUID = 1L;
	
	// member 패키지에 선언해놓은 member 엔티티를 사용하기 위해 선언
	private Member member;

	public MemberPrincipalDetails(Member member) {
		this.member = member;
	}

	// 생성자
	public Member getMember() {
		return member;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(member.getRole()));
		return authorities;
	}

	@Override
	public String getPassword() {
		return member.getPassword();
	}

	@Override
	public String getUsername() {
		return member.getUsername();
	}

	public String getName() {
		return member.getName();
	}

	public String getEmail() {
		return member.getEmail();
	}

	public String getPhone() {
		return member.getPhone();
	}

	public String getSignDate() {
		return member.getSignDate();
	}

	public Long getUserNo() {
		return member.getUserNo();
	}

	public MemberDto toMemberDto() {
		MemberDto memberDto = new MemberDto();
		memberDto.setUserNo(member.getUserNo());
		memberDto.setName(member.getName());
		memberDto.setPhone(member.getPhone());
		memberDto.setEmail(member.getEmail());
		String date = new Date().toString();
		memberDto.setSignDate(date);
		return memberDto;
	}

	// 계정이 만료되지 않았는지를 담아두기 위해 (true: 만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	// 계정이 잠겨있지 않았는지를 담아두기 위해 (true: 잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	// 계정의 비밀번호가 만료되지 않았는지를 담아두기 위해 (true: 만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	// 계정이 활성화되어있는지를 담아두기 위해 (true: 활성화됨)
	@Override
	public boolean isEnabled() {
		return true;
	}

}