package com.runner.ddida.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.runner.ddida.model.Reserve;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Long> {
	
    List<Reserve> findByRsrcNo(String rsrcNo);
    
    List<Reserve> findByRsrcNoAndUseDateAfter(String rsrcNo, String today);
    
    @Query("SELECT rt.useTime FROM ReserveTime rt " +
    	       "WHERE rt.reserve.id IN (" +
    	       "    SELECT r.id FROM Reserve r " +
    	       "    WHERE r.rsrcNo LIKE :rsrcNo AND r.useDate = :useDate)")
    List<String> findUseTimeByRsrcNoAndUseDate(@Param("rsrcNo") String rsrcNo, @Param("useDate") String useDate);
}
