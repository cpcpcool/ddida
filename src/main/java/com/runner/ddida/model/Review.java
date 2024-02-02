package com.runner.ddida.model;

import java.time.LocalDate;

import com.runner.ddida.dto.ReviewDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_no")
	private Long reviewNo;
	
	@Column(name = "reserve_id")
	private Long reserveId;
	
	@Column(name = "username")
	private String userName;
	
	@Column(name = "review_description")
	private String reviewDesc;
	
	@Column(name = "review_date")
	private LocalDate reviewDate;
	
	public ReviewDto toReviewDto() {
		return ReviewDto.builder()
				.reviewNo(reviewNo)
				.reserveId(reserveId)
				.userName(userName)
				.reviewDesc(reviewDesc)
				.reviewDate(LocalDate.now())
				.build();
	}

}
