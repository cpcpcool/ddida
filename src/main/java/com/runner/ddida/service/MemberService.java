package com.runner.ddida.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.runner.ddida.model.Member;
import com.runner.ddida.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

/**
 * @author 박재용
 */

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	// 전체 페이징
	public Page<Member> findAll(Pageable pageable) {
		return memberRepository.findAll(pageable);
	}

	// 상세
	public Optional<Member> findByUserNo(Long userNo) {
		return memberRepository.findByUserNo(userNo);
	}

	// 검색 페이징
	public Page<Member> searchUsers(String searchKeyword, String searchType, Pageable pageable) {
		if (searchKeyword == null || searchType == null) {
			return memberRepository.findAll(pageable);
		} else {
			switch (searchType) {
			case "username":
				return memberRepository.findByUsernameContaining(searchKeyword, pageable);
			case "name":
				return memberRepository.findByNameContaining(searchKeyword, pageable);
			case "role":
				return memberRepository.findByRoleContaining(searchKeyword, pageable);
			case "signDate":
				return memberRepository.findBySignDateContaining(searchKeyword, pageable);
			default:
				return memberRepository.findAll(pageable);
			}
		}
	}

}
