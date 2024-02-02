package com.runner.ddida.dto;

import java.time.LocalDate;

import com.runner.ddida.model.Review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
	
	private Long reviewNo;
	private Long reserveId;
	private String userName;
	private String reviewDesc;
	private LocalDate reviewDate;
	
	public Review toEntity() {
		return Review.builder()
				.reviewNo(reviewNo)
				.reserveId(this.reserveId)
				.userName(userName)
				.reviewDesc(reviewDesc)
				.reviewDate(LocalDate.now())
				.build();
	}

}
