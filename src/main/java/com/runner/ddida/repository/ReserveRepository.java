package com.runner.ddida.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.runner.ddida.model.Reserve;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Long> {
	
	
}
