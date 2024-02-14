package com.runner.ddida.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.runner.ddida.dto.QnaDto;
import com.runner.ddida.model.Qna;
import com.runner.ddida.repository.QnaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaService {
	private final QnaRepository qnaRepository;

	// 문의 목록 - 김민혜
	public Page<Qna> findAll(Pageable pageable) {
		return qnaRepository.findAll(pageable);
	}

	// 문의 상세 - 김민혜
	public Optional<Qna> findByQnaNo(Long qnaNo) {
		return qnaRepository.findByQnaNo(qnaNo);
	}

	// 이전 글 - 김민혜
	public Qna prev(Long qnaNo) {
		return qnaRepository.prev(qnaNo);
	}

	// 다음 글 - 김민혜
	public Qna next(Long qnaNo) {
		return qnaRepository.next(qnaNo);
	}

	// 조회수 증가 - 김민혜
	public Integer viewcnt(Long qnaNo) {
		return qnaRepository.viewcnt(qnaNo);
	}

	// 문의 등록 - 김민혜
	public QnaDto save(QnaDto qnaDto) {
		Qna qna = qnaDto.toEntity();

		Qna savedQna = qnaRepository.save(qna);

		return savedQna.toQnaDto();
	}

	// 문의 수정 - 김민혜
	public QnaDto getQna(Long qnaNo) {
		Qna qna = qnaRepository.findByQnaNo(qnaNo).get();

		QnaDto qnaDto = QnaDto.builder().qnaNo(qna.getQnaNo()).title(qna.getTitle()).description(qna.getDescription())
				.build();
		return qnaDto;
	}

	// 문의 삭제 - 김민혜
	public void deleteQna(Long qnaNo) {
		qnaRepository.deleteById(qnaNo);
	}

	// 제목으로 검색된 문의 목록 - 김민혜
	public Page<Qna> findByTitleContaining(String searchKeyword, Pageable pageable) {
		return qnaRepository.findByTitleContaining(searchKeyword, pageable);
	}

	// 내용으로 검색된 문의 목록 - 김민혜
	public Page<Qna> findByDescriptionContaining(String searchKeyword, Pageable pageable) {
		return qnaRepository.findByDescriptionContaining(searchKeyword, pageable);
	}

	// [관리자] 문의글번호로 검색된 문의 목록 - 노윤건 24.02.04
	public Page<Qna> findByQnaNoContaining(String searchKeyword, Pageable pageable) {
		return qnaRepository.findByDescriptionContaining(searchKeyword, pageable);
	}

	// [관리자] 검색 페이징
		public Page<Qna> searchQna(String searchKeyword, String searchType, Pageable pageable) {
			if (searchKeyword == null || searchType == null) {
				return qnaRepository.findAll(pageable);
			} else {

				switch (searchType) {
				case "title":
					return qnaRepository.findByTitleContaining(searchKeyword, pageable);
				case "username":
					return qnaRepository.findByUsernameContaining(searchKeyword, pageable);
				case "qnaDate":
					return qnaRepository.findByQnaDateContaining(searchKeyword, pageable);
				default:
					return qnaRepository.findAll(pageable);
				}
			}
		}
		
		// [관리자] 회원 상세페이지 오른쪽 표에 정보 표시
		public List<Qna> findByUserNo(Long userNo) {
			return qnaRepository.findByUserNo(userNo);
		}
		
		// [관리자] 답변내용 qna테이블로 저장 24.02.04 노윤건
	    public void saveAnswer(Long qnaNo, String answer) {
	    	LocalDateTime currentDateTime = LocalDateTime.now();
	    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	    	String formattedDateTime = currentDateTime.format(formatter);
	        qnaRepository.saveAnswer(qnaNo, answer, formattedDateTime);
	    }

}
