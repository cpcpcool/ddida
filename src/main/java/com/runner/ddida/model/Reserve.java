package com.runner.ddida.model;

import lombok.AllArgsConstructor;
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
public class Reserve {
	private String rsrcNm;
	private String useStartDate;
	private String useStartTime;
	private String reserveFee;
	private String userName;
	private String userPhoneOne;
	private String userPhoneTwo;
	private String userPhoneThr;
	private String userEmailOne;
	private String userEmailTwo;
}
