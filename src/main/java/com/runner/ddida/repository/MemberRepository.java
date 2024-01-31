package com.runner.ddida.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.runner.ddida.model.Member;


public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByUsername(String username);
	
	//중복방지

}
