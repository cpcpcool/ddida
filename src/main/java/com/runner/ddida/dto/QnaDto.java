package com.runner.ddida.dto;

import java.time.LocalDate;

import com.runner.ddida.model.Qna;

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
public class QnaDto {
	
	private Long qnaNo;
	private Long userNo;
	private String title;
	private String description;
	private LocalDate qnaDate;
	private Integer qnaView;
	private boolean qnaSecret;
	
	public Qna toEntity() {
		return Qna.builder()
				.qnaNo(this.qnaNo)
				.userNo(this.userNo)
				.title(title)
				.description(description)
				.qnaDate(LocalDate.now())
				.qnaView(0)
				.build();
	}

}
