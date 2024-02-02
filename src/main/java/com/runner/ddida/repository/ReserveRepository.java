package com.runner.ddida.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import com.runner.ddida.model.Reserve;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
	
	List<Reserve> findAll();
	
	/*예약 목록*/
	Page<Reserve> findAll(Pageable pageable);
	
	/* 시설 이름으로 검색된 글 목록 */
	Page<Reserve> findBySpaceNameContaining(String searchKeyword, Pageable pageable);
	
	/* 예약 날짜로 검색된 글 목록 */
	Page<Reserve> findByUseDateContaining(String searchKeyword, Pageable pageable);
	
	/* 예약 상세 */
	Optional<Reserve> findByReserveId(Long reserveId);
	
}
