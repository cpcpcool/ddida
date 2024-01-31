package com.runner.ddida.dto;

import com.runner.ddida.enums.MemberRole;
import com.runner.ddida.model.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

	private Long userNo;
	
	private String username;

	private String password;	
		
	private String role;
	
	private  String name;
	
	private String phone;
	
	private String email;
	
	private String signDate;

	public Member toEntity() {
		return Member.builder()
				.userNo(userNo)
				.username(username)
				.password(password)
				.role(role)
				.name(name)
				.phone(phone)
				.email(email)
				.signDate(signDate)
				.build();
	}
	
}
