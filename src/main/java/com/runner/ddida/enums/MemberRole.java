package com.runner.ddida.enums;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberRole {
	// 기본적으로 final static
	USER("ROLE_USER", "이용자"),
	ADMIN("ROLE_ADMINa", "관리자"),;
	
	private final String roleName;
	private final String koName;
	
	public final String roleName() {
		return roleName;
	}
	
	public final String koName() {
		return koName;
	}
	
}
