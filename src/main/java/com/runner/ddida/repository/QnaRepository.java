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

import com.runner.ddida.model.Qna;

public interface QnaRepository extends JpaRepository<Qna, Long> {
	/* 문의 목록 - 김민혜 */
	Page<Qna> findAll(Pageable pageable);
	
	/* 제목으로 검색된 글 목록 - 김민혜 */
	Page<Qna> findByTitleContaining(String searchKeyword, Pageable pageable);
	
	/* 내용으로 검색된 글 목록 - 김민혜 */
	Page<Qna> findByDescriptionContaining(String searchKeyword, Pageable pageable);
	
	/* 문의 상세 - 김민혜 */
	Optional<Qna> findByQnaNo(Long qnaNo);
	
	/* 이전 글 - 김민혜 */
	@Query(value = "select * from qna where qna_no < :qnaNo order by qna_no desc limit 1", nativeQuery = true)
	Qna prev(@Param("qnaNo") Long qnaNo);

	/* 다음 글 - 김민혜 */
	@Query(value = "select * from qna where qna_no > :qnaNo order by qna_no asc limit 1", nativeQuery = true)
	Qna next(@Param("qnaNo") Long qnaNo);
	
	/* 조회수 증가 - 김민혜 */
	@Transactional
	@Modifying
	@Query(value = "update qna set qna_view = qna_view + 1 where qna_no = :qnaNo", nativeQuery = true)
	Integer viewcnt(@Param("qnaNo") Long qnaNo);
	
	// [관리자] 회원 상세페이지 오른쪽 표에 정보 표시
	@Query(value = "SELECT qna.* " +
            "FROM qna " +
            "WHERE qna.username IN (SELECT member.username FROM member WHERE member.user_no = :userNo);",
    nativeQuery = true)
	List<Qna> findByUserNo(@Param("userNo") Long userNo);
	
	/* [관리자] 문의 상세(이름까지 같이 표시) 24.02.04 노윤건 */
    @Query(value = "SELECT q, m.name AS user_name "
    		+ "FROM Qna q JOIN Member m ON q.username = m.username "
    		+ "WHERE q.qnaNo = :qnaNo", nativeQuery = true)
    Optional<Object[]> findQnaAndUsernameByQnaNo(@Param("qnaNo") Long qnaNo);

    // [관리자] 답변내용 qna테이블로 저장 24.02.04 노윤건
    @Transactional
    @Modifying
    @Query("UPDATE Qna q SET q.answer = :answer WHERE q.qnaNo = :qnaNo")
    void saveAnswer(@Param("qnaNo") Long qnaNo, @Param("answer") String answer);
    
	// [관리자] 문의글번호로 검색된 문의 목록 - 노윤건 24.02.04
	@Query(value = "select * from qna where qna_no = :searchKeyword", nativeQuery = true)
	Page<Qna> findByQnaNoContaining(@Param("searchKeyword") Long searchKeyword, Pageable pageable);	
	
	// [관리자] 아이디로 검색된 문의 목록 - 노윤건 24.02.04
	Page<Qna> findByUsernameContaining(String searchKeyword, Pageable pageable);
	
	// [관리자] 작성일자로 검색된 문의 목록 - 노윤건 24.02.04
	Page<Qna> findByQnaDateContaining(String searchKeyword, Pageable pageable);
 
	// [관리자] userNoList를 받아 문의글 수 카운팅 - 노윤건 24.02.11
	@Query(value = "select q.username, count(q.qna_no) as qnaCount "
			+ "from qna q where q.username in "
			+ "(select m.username from member m where m.user_no in :userNos) group by q.username", nativeQuery = true)
	List<Object[]> count(@Param("userNos") List<Long> userNos);
}
