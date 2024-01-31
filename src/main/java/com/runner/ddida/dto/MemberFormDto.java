package com.runner.ddida.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.runner.ddida.model.Member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberFormDto {

	@NotBlank(message = "아이디를 입력해주세요")
	private String username;

	@NotBlank(message = "비밀번호를 입력해주세요")
	private String password;	
	
	private String role;
	
	@NotBlank(message = "이름을 입력해주세요")
	private String name;

	@NotBlank(message = "전화번호를 입력해주세요")
	private String phone;
	
	@NotBlank(message = "이메일을 입력해주세요")
//	@Email(message = "이메일 형식이 아닙니다")
	private String email;

	public Member toEntity() {
		return Member.builder()
				.username(username)
				.password(password)
				.role(role)
				.name(name)
				.phone(phone)
				.email(email)
				.build();
	}
	
		
	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}

}
