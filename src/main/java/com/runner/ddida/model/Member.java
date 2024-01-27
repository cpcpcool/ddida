package com.runner.ddida.model;

import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.runner.ddida.dto.MemberDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_no")
	private Long userNo;

	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "password")
	private String password;

//	@Column(name = "role")
//	@Enumerated(EnumType.STRING)
//	private MemberRole role;
	
	@Column(name = "role")
	private String role;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "phone")
	private String phone;

	@Column(name = "email")
	private String email;

	@Column(name = "sign_date")
	private String signDate = new Date().toString();

//	private int active;
//
//	private String roles = "";
//
//	private String permissions = "";

	@Builder
	public Member(Long userNo, String username, String password, String role, String name, String phone, String email,
			String signDate) {
		this.userNo = userNo;
		this.username = username;
		this.password = password;
		this.role = role;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.signDate = new Date().toString();

//		this.roles = roles;
//		this.permissions = permissions;
//		this.active = 1;
	}

//	public List<String> getRoleList() {
//		if (this.roles.length() > 0) {
//			return Arrays.asList(this.roles.split(","));
//		}
//
//		return new ArrayList<>();
//	}
//
//	public List<String> getPermissionList() {
//		if (this.permissions.length() > 0) {
//			return Arrays.asList(this.permissions.split(","));
//		}
//
//		return new ArrayList<>();
//	}

	public static Member createMember(MemberDto memberDto, PasswordEncoder passwordEncoder) {
        Member member = Member.builder()
        		.username(memberDto.getUsername())
        		.password(passwordEncoder.encode(memberDto.getPassword()))  //암호화처리
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .role(memberDto.getRole())
                .build();
        return member;
        }
	
	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}
        
}
