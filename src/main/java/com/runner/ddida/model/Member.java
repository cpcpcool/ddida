package com.runner.ddida.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.runner.ddida.dto.MemberDto;
import com.runner.ddida.dto.MemberFormDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@DynamicUpdate
@Table(name = "member")
public class Member implements UserDetails {

	/**
	 * @author 박재용
	 */

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_no")
	private Long userNo;

	@Column(name = "username", unique = true, nullable = true)
	private String username;

	@Column(name = "password", nullable = true)
	private String password;

	@Column(name = "role", nullable = true)
	private String role;

	// enum 관련
//	@Builder.Default
//	@Enumerated(EnumType.STRING)
//	@CollectionTable(name = "role")
//	@ElementCollection(fetch = FetchType.EAGER)
//	private Set<MemberRole> role = new HashSet<>();
//	public Member addRole(MemberRole memberRole) {
//		role.add(memberRole);
//		return this;
//	}
//	
//	public String getRoleName() {
//		String roleName = null;
//		for(MemberRole memberRole : role) {
//			roleName = memberRole.roleName();
//		}
//		return roleName; 
//	}
//	
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
		this.signDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	public MemberDto toMemberDto() {
		return MemberDto.builder()
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
	
	public MemberFormDto toMemberFormDto() {
		return MemberFormDto.builder()
				.username(username)
				.password(password)
				.name(name)
				.phone(phone)
				.email(email)
				.build();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.role));
		return authorities;
	}
	
	//비밀번호 변경용
	public void setPassword(String password) {
		this.password = password;
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
