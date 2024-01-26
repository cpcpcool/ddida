package com.runner.ddida.model;

import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userNo;

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "password")
	private String password;
	@Column(name = "name")
	private String name;
	@Column(name = "role")
	private String role;
	@Column(name = "phone")
	private String phone;
	@Column(name = "email")
	private String email;
	@Column(name = "join")
	private String join;
	
//	public void encodePassword(PasswordEncoder passwordEncoder) {
//		this.password = passwordEncoder.encode(this.password);
//	}
}
