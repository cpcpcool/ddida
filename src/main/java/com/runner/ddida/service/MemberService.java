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

	// 검색 페이징
//	public Page<Member> searchUsers(String searchKeyword, String searchType, Pageable pageable) {
//		if (searchKeyword == null || searchType == null) {
//			return memberRepository.findAll(pageable);
//		} else {
//			switch (searchType) {
//			case "username":
//				return memberRepository.findByUsernameContaining(searchKeyword, pageable);
//			case "name":
//				return memberRepository.findByNameContaining(searchKeyword, pageable);
//			case "role":
//				return memberRepository.findByRoleContaining(searchKeyword, pageable);
//			case "signDate":
//				return memberRepository.findBySignDateContaining(searchKeyword, pageable);
//			default:
//				return memberRepository.findAll(pageable);
//			}
//		}
//	}

	public Map<String, Object> searchUsers(String searchKeyword, String searchType, Pageable pageable) {
		Map<String, Object> result = new HashMap<>();
		List<Long> userNoList = memberRepository.getUserNoList();
		List<Object[]> qnaCounts = qnaRepository.count(userNoList);
		result.put("qnaCounts", qnaCounts);
		System.out.println("===================================");
		for(Object[] row: qnaCounts) {
			String username = (String)row[0];
			Long qnaCount = (Long)row[1];
			System.out.println("아이디 : " + username);
			System.out.println("작성 문의글 수 : " + qnaCount);
		}
		System.out.println("===================================");
		
		List<Long[]> userStatistics = reserveRepository.getUserStatistics(userNoList);
		result.put("userStats", userStatistics);
		for(Long[] row: userStatistics) {
			Long userNo = row[0];
			Long reserveCount = row[1];
			Long reviewCount = row[2];
			System.out.println("사용자고유번호 :" + userNo);
			System.out.println("예약횟수 : " + reserveCount);
			System.out.println("작성 후기 수 : " + reviewCount);
			System.out.println();
		}
		
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
