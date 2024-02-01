package com.runner.ddida.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.runner.ddida.model.Qna;

public interface QnaRepository extends JpaRepository<Qna, Long> {
	/* 문의 목록 */
	Page<Qna> findAll(Pageable pageable);
	
	/* 제목으로 검색된 글 목록 */
	Page<Qna> findByTitleContaining(String searchKeyword, Pageable pageable);
	
	/* 내용으로 검색된 글 목록 */
	Page<Qna> findByDescriptionContaining(String searchKeyword, Pageable pageable);
	
	/* 문의 상세 */
	Optional<Qna> findByQnaNo(Long qnaNo);
	
	/* 이전 글 */
	@Query(value = "select * from qna where qna_no < :qnaNo order by qna_no desc limit 1", nativeQuery = true)
	Qna prev(@Param("qnaNo") Long qnaNo);

	/* 다음 글 */
	@Query(value = "select * from qna where qna_no > :qnaNo order by qna_no asc limit 1", nativeQuery = true)
	Qna next(@Param("qnaNo") Long qnaNo);
	
	/* 조회수 증가 */
	@Transactional
	@Modifying
	@Query(value = "update qna set qna_view = qna_view + 1 where qna_no = :qnaNo", nativeQuery = true)
	Integer viewcnt(@Param("qnaNo") Long qnaNo);
}
