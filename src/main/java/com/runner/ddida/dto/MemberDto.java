package com.runner.ddida.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

	private Long userNo;

	@Size(min = 3, max = 10)
	@NotEmpty(message = "필수 항목입니다.")
	private String username;

	@NotEmpty(message = "필수 항목입니다.")
	private String password;
		
	private String role;

	@NotEmpty(message = "필수 항목입니다.")
	private  String name;
	
	private String phone;
	
	private String email;
	
	private String signDate;

}
