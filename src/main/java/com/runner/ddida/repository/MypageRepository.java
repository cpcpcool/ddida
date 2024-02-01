package com.runner.ddida.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.runner.ddida.model.Member;

public interface MypageRepository extends JpaRepository<Member, Long> {

	
		
}
