package com.runner.ddida.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

	private Long userNo;
	private String username;
	private String password;
	private String name;
	private String role;
	private String phone;
	private String email;
	private String join;
	
	
}
