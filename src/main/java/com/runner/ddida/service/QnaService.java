package com.runner.ddida.service;

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
	
	// 문의 목록
	public Page<Qna> findAll(Pageable pageable) {
		return qnaRepository.findAll(pageable);
	}
	
	// 제목으로 검색된 문의 목록
	public Page<Qna> findByTitleContaining(String searchKeyword, Pageable pageable) {
		return qnaRepository.findByTitleContaining(searchKeyword, pageable);
	}
	
	// 내용으로 검색된 문의 목록
	public Page<Qna> findByDescriptionContaining(String searchKeyword, Pageable pageable) {
		return qnaRepository.findByDescriptionContaining(searchKeyword, pageable);
	}
		
	// 문의 상세
	public Optional<Qna> findByQnaNo(Long qnaNo) {
		return qnaRepository.findByQnaNo(qnaNo);
	}
	
	// 이전 글
	public Qna prev(Long qnaNo) {
		return qnaRepository.prev(qnaNo);
	}
	
	// 다음 글
	public Qna next(Long qnaNo) {
		return qnaRepository.next(qnaNo);
	}
	
	// 조회수 증가
	public Integer viewcnt(Long qnaNo) {
		return qnaRepository.viewcnt(qnaNo);
	}
	
	// 문의 등록
	public QnaDto save(QnaDto qnaDto) {
		Qna qna = qnaDto.toEntity();
		
		Qna savedQna = qnaRepository.save(qna);
		
		return savedQna.toQnaDto();
	}
	
	// 문의 수정
	public Integer edit(Qna qna) {
		return qnaRepository.edit(qna);
	}
}
