package com.runner.ddida.repository;

import java.awt.print.Pageable;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.runner.ddida.model.Member;


public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByUsername(String username);
	
	// 아이디(username) 중복방지
	boolean existsByUsername(String username);

	/* 문의 목록 */
	Page<Member> findAll(Pageable pageable);
	
}
