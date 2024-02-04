package com.runner.ddida.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.runner.ddida.model.ReserveTime;

public interface ReserveTimeRepository extends JpaRepository<ReserveTime, Long> {
	
	@Query(value = "select use_time from reserve_time where reserve_id = :reserveId", nativeQuery = true)
	List<String> findUseTimeByReserveId(@Param("reserveId") Long reserveId);
}
