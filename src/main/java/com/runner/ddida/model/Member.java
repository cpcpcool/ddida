package com.runner.ddida.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class) // 변경될때 자동기록
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

	@Column(name = "name", nullable = true)
	private String name;

	@Column(name = "phone", nullable = true)
	private String phone;

	@Column(name = "email", nullable = true)
	private String email;

	@Column(name = "sign_date", updatable = false)
	private String signDate;
	
	@PrePersist
	public void onPrePersist() {
		this.signDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
	}

//	private int active;
//
//	private String roles = "";
//
//	private String permissions = "";

//	public Member(Long userNo, String username, String password, String role) {
//		this.userNo = userNo;
//		this.username = username;
//		this.password = password;
//		this.role = role;
//	}

	public Member(Long userNo, String username, String password, String role, String name, String phone, String email,
			String signDate) {
		this.userNo = userNo;
		this.username = username;
		this.password = password;
		this.role = role;
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.signDate = signDate;

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

//	public static Member createMember(MemberDto memberDto, PasswordEncoder passwordEncoder) {
//        Member member = Member.builder()
//        		.username(memberDto.getUsername())
//        		.password(passwordEncoder.encode(memberDto.getPassword()))  //암호화처리
//                .name(memberDto.getName())
//                .email(memberDto.getEmail())
//                .role(memberDto.getRole())
//                .build();
//        return member;
//        }

	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}

}
