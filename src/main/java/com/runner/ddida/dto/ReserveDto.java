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
	private String useStartDate;
	private String useStartTime;
	private int reserveFee;
	private String userName;
	private String userPhoneOne;
	private String userPhoneTwo;
	private String userPhoneThr;
	private String userEmailOne;
	private String userEmailTwo;
	private Long userNo;
	private String rsrcNo;
	private String review;
}
