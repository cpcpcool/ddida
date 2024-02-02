package com.runner.ddida.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.runner.ddida.model.Reserve;
import com.runner.ddida.repository.ReserveRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReserveService {
	
	private final ReserveRepository reserveRepository;
	
	public List<Reserve> findAll() {
		return reserveRepository.findAll();
	}
	
	/* 예약 목록 */
	public Page<Reserve> findAll(Pageable pageable) {
		return reserveRepository.findAll(pageable);
	}
	
	/* 시설 이름으로 검색된 예약 목록 */
	public Page<Reserve> findBySpaceNameContaining(String searchKeyword, Pageable pageable) {
		return reserveRepository.findBySpaceNameContaining(searchKeyword, pageable);
	}
	
	/* 이용 날짜로 검색된 예약 목록 */
	public Page<Reserve> findByUseDateContaining(String searchKeyword, Pageable pageable) {
		return reserveRepository.findByUseDateContaining(searchKeyword, pageable);
	}
	
	/* 예약 상세 */
	public Optional<Reserve> findByReserveId(Long reserveId) {
		return reserveRepository.findByReserveId(reserveId);
		
	}

}
