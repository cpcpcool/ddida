package com.runner.ddida.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.runner.ddida.model.Reserve;

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
	
    List<Reserve> findByRsrcNo(String rsrcNo);
    
    List<Reserve> findByRsrcNoAndUseDateAfter(String rsrcNo, String today);
    
    @Query("SELECT rt.useTime FROM ReserveTime rt " +
    	       "WHERE rt.reserve.id IN (" +
    	       "    SELECT r.id FROM Reserve r " +
    	       "    WHERE r.rsrcNo LIKE :rsrcNo AND r.useDate = :useDate)")
    List<String> findUseTimeByRsrcNoAndUseDate(@Param("rsrcNo") String rsrcNo, @Param("useDate") String useDate);
	
	List<Reserve> findAll();
	
	/*예약 목록*/
	Page<Reserve> findAllByUserNo(@Param("userNo") Long userNo, Pageable pageable);
	
	/* 시설 이름으로 검색된 글 목록 */
	Page<Reserve> findAllByUserNoAndRsrcNmContaining(@Param("userNo") Long userNo, @Param("searchKeyword") String rsrcNm, Pageable pageable);
	
	/* 예약 날짜로 검색된 글 목록 */
	Page<Reserve> findAllByUserNoAndUseDateContaining(@Param("userNo") Long userNo, @Param("searchType") String searchKeyword, Pageable pageable);
	
	/* 예약 상세 */
	Optional<Reserve> findByReserveId(Long reserveId);
	
	/* 예약 시간 */
	List<Reserve> findUseTimeByReserveId(Long reserveId);
	
	/* 이용 완료 시 이용 완료 여부 1 */
	@Transactional
	@Modifying
	@Query(value = "update reserve set checkout = 1 where reserve_id = :reserveId", nativeQuery = true)
	Integer checkout(@Param("reserveId") Long reserveId);
	
	// 이용후기 수, 이용 횟수 조회 24.02.05 노윤건
    @Query(value = "SELECT " +
            "m.user_no, " +
            "(SELECT COUNT(*) FROM reserve r WHERE r.user_no = m.user_no) AS reserve_count, " +
            "(SELECT COUNT(*) FROM reserve r WHERE r.user_no = m.user_no AND r.review IS NOT NULL) AS review_count " +
            "FROM member m " +
            "WHERE m.user_no IN :userNoList", nativeQuery = true)
    List<Long[]> getUserStatistics(@Param("userNoList") List<Long> userNoList);
}
