package com.runner.ddida.model;

import java.time.LocalDate;

import com.runner.ddida.dto.QnaDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "qna")
public class Qna {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "qna_no")
	private Long qnaNo;
	
	@Column(name = "username")
	private String userName;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "qna_date")
	private LocalDate qnaDate;
	
	@Column(name = "qna_view")
	private Integer qnaView;
	
	@Column(name = "qna_secret")
	private boolean qnaSecret;
	
	public QnaDto toQnaDto() {
		return QnaDto.builder()
				.qnaNo(qnaNo)
				.userName(userName)
				.title(title)
				.description(description)
				.qnaDate(LocalDate.now())
				.qnaView(0)
				.build();
	}

}
