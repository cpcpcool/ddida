package com.runner.ddida.service;

import org.springframework.stereotype.Service;

import com.runner.ddida.dto.ReviewDto;
import com.runner.ddida.model.Review;
import com.runner.ddida.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewService {
	private final ReviewRepository reviewRepository;
	
	// 리뷰 등록
	public ReviewDto save(ReviewDto reviewDto) {
		Review review = reviewDto.toEntity();
		
		Review savedReview = reviewRepository.save(review);
		
		return savedReview.toReviewDto();
	}

}
