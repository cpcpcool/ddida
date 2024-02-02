package com.runner.ddida.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.runner.ddida.model.ReserveTime;

@Repository
public interface ReserveTimeRepository extends JpaRepository<ReserveTime, Long> {
	
}
