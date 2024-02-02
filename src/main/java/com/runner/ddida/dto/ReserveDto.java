package com.runner.ddida.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
public class ReserveDto {
	private String rsrcNm;
	private String useDate;
	private String useTime;
	private String reserveFee;
	private String userName;
	private String userPhone;
	private String userEmail;
	private Long userNo;
	private String rsrcNo;
	private String review;
}
