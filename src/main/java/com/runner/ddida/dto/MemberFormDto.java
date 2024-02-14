package com.runner.ddida.dto;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.runner.ddida.model.Member;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 박재용
 */

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
	
	private String name;

	private String phone;

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

