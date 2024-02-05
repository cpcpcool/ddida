package com.runner.ddida.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.runner.ddida.model.Member;

/**
 * @author 박재용
 */

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByUsername(String username);
	
	// 아이디(username) 중복방지
	boolean existsByUsername(String username);

	// 회원 목록
	Page<Member> findAll(Pageable pageable);
	
	// 회원 상세
	Optional<Member> findByUserNo(Long userNo);

	// 회원 검색
	// 아이디
	Page<Member> findByUsernameContaining(String searchKeyword, Pageable pageable);
	// 이름
	Page<Member> findByNameContaining(String searchKeyword, Pageable pageable);
	// 권한
	Page<Member> findByRoleContaining(String searchKeyword, Pageable pageable);
	//  가입일
	Page<Member> findBySignDateContaining(String searchKeyword, Pageable pageable);

	// [관리자] find userNo
	@Query(value = "select user_no from member", nativeQuery = true)
	List<Long> getUserNoList();
	
} 
