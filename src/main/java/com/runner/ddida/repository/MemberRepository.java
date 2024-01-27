package com.runner.ddida.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.runner.ddida.model.Member;


public interface MemberRepository extends JpaRepository<Member, Long> {

	Member findByUsername(String username);
	
	//중복방지

}
