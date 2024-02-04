package com.runner.ddida.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.runner.ddida.model.Reserve;
import com.runner.ddida.repository.ReserveRepository;
import com.runner.ddida.repository.ReserveTimeRepository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReserveService {

	private final ReserveRepository reserveRepository;
	private final ReserveTimeRepository reserveTimeRepository;
	private final EntityManager entityManager;
	
	public List<Reserve> findAll() {
		return reserveRepository.findAll();
	}

	/* 예약 목록 */
	public Page<Reserve> findAllByUsername(Long userNo, Pageable pageable) {
	@Transactional
	public Page<Reserve> findAllByUsername(Long userNo, Pageable pageable) {

		Page<Reserve> reserveList = reserveRepository.findAllByUserNo(userNo, pageable);
		
		for (Reserve reserve : reserveList) {
			LocalDate now = LocalDate.now();
			LocalDate useDate = LocalDate.parse(reserve.getUseDate());
			if (now.isAfter(useDate)) {
				reserveRepository.checkout(reserve.getReserveId());
			}
		}

		entityManager.flush();
		
		return reserveList; 
	}

	/* 시설 이름으로 검색된 예약 목록 */
	public Page<Reserve> findByRsrcNmContaining(Long userNo, String searchKeyword, Pageable pageable) {
		return reserveRepository.findAllByUserNoAndRsrcNmContaining(userNo, searchKeyword, pageable);
	}

	/* 이용 날짜로 검색된 예약 목록 */
	public Page<Reserve> findByUseDateContaining(Long userNo, String searchKeyword, Pageable pageable) {
		return reserveRepository.findAllByUserNoAndUseDateContaining(userNo, searchKeyword, pageable);
	}

	/* 예약 상세 */
	public Optional<Reserve> findByReserveId(Long reserveId) {
		return reserveRepository.findByReserveId(reserveId);
	}

	/* 예약 취소 */
	public void cancel(Long reserveId) {
		reserveRepository.deleteById(reserveId);
	}

	/* 예약 번호로 불러온 예약 시간 */
	public List<String> findUseTimeByReserveId(Long reserveId) {
		return reserveTimeRepository.findUseTimeByReserveId(reserveId);
	}
	
	/* 이용 완료 여부 */
	public Integer checkout(Long reserveId) {
		return reserveRepository.checkout(reserveId);
	}
	
	
}
