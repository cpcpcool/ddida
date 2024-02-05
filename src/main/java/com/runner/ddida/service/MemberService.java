package com.runner.ddida.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.runner.ddida.model.Member;
import com.runner.ddida.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

/**
 * @author 박재용
 */

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
//	private final QnaRepository qnaRepository;
//	private final ReserveRepository reserveRepository;
	
	// 전체 페이징
	public Page<Member> findAll(Pageable pageable) {
		return memberRepository.findAll(pageable);
	}

	// 상세
	public Optional<Member> findByUserNo(Long userNo) {
		return memberRepository.findByUserNo(userNo);
	}

	// 검색 페이징
	public Page<Member> searchUsers(String searchKeyword, String searchType, Pageable pageable) {
		if (searchKeyword == null || searchType == null) {
			return memberRepository.findAll(pageable);
		} else {
			switch (searchType) {
			case "username":
				return memberRepository.findByUsernameContaining(searchKeyword, pageable);
			case "name":
				return memberRepository.findByNameContaining(searchKeyword, pageable);
			case "role":
				return memberRepository.findByRoleContaining(searchKeyword, pageable);
			case "signDate":
				return memberRepository.findBySignDateContaining(searchKeyword, pageable);
			default:
				return memberRepository.findAll(pageable);
			}
		}
	}

//	public Map<String, Object> searchUsers(String searchKeyword, String searchType, Pageable pageable) {
//		List<Long> userNoList = memberRepository.getUserNoList();
//		List<Long[]> userStatistics = reserveRepository.getUserStatistics(userNoList);
//		Map<String, Object> result = new HashMap<>();
//		
//		result.put("userStats", userStatistics);
//		
//		for(Long[] row: userStatistics) {
//			Long userNo = row[0];
//			Long reserveCount = row[1];
//			Long reviewCount = row[2];
//			System.out.println("userNo :" + userNo);
//			System.out.println("reserveCount : " + reserveCount);
//			System.out.println("reviewCount : " + reviewCount);
//		}
//		
//		if (searchKeyword == null || searchType == null) {
//			result.put("result", qnaRepository.findAll(pageable));
//		} else {
//			switch (searchType) {
//			case "qnaNo":
//				Long longKeyword = Long.parseLong(searchKeyword);
//				result.put("result", qnaRepository.findByQnaNoContaining(longKeyword, pageable));
//			case "title":
//				result.put("result", qnaRepository.findByTitleContaining((String) searchKeyword, pageable));
//			case "userName":
//				result.put("result", qnaRepository.findByUsernameContaining((String) searchKeyword, pageable));
//			case "qnaDate":
//				result.put("result", qnaRepository.findByQnaDateContaining((String) searchKeyword, pageable));
//			default:
//				result.put("result", qnaRepository.findAll(pageable));
//			}
//		}
//		return result;
//	}
}
