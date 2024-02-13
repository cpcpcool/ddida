package com.runner.ddida.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.runner.ddida.model.Member;
import com.runner.ddida.repository.MemberRepository;
import com.runner.ddida.repository.QnaRepository;
import com.runner.ddida.repository.ReserveRepository;

import lombok.RequiredArgsConstructor;

/**
 * @author 박재용
 */

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final QnaRepository qnaRepository;
	private final ReserveRepository reserveRepository;
	
	// 전체 페이징
	public Page<Member> findAll(Pageable pageable) {
		return memberRepository.findAll(pageable);
	}

	// 상세
	public Optional<Member> findByUserNo(Long userNo) {
		return memberRepository.findByUserNo(userNo);
	}

	public Map<String, Object> searchUsers(String searchKeyword, String searchType, Pageable pageable) {
		Map<String, Object> result = new HashMap<>();
		List<Long> userNoList = memberRepository.getUserNoList();
		List<Object[]> qnaCounts = qnaRepository.count(userNoList);
		result.put("qnaCounts", qnaCounts);
		
		List<Long[]> userStatistics = reserveRepository.getUserStatistics(userNoList);
		result.put("userStats", userStatistics);
		if (searchKeyword == null || searchType == null) {
			result.put("result", memberRepository.findAll(pageable));
		} else {
			switch (searchType) {
			case "userName":
				result.put("result", memberRepository.findByUsernameContaining((String)searchKeyword, pageable));
			case "role":
				result.put("result", memberRepository.findByRoleContaining((String)searchKeyword, pageable));
			case "signDate":
				result.put("result", memberRepository.findBySignDateContaining((String)searchKeyword, pageable));
			default:
				result.put("result", memberRepository.findAll(pageable));
			}
		}
		return result;
	}
	
}
