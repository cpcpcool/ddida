package com.runner.ddida.dto;

import java.util.List;

import com.runner.ddida.model.Reserve;
import com.runner.ddida.model.ReserveTime;

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
	private String reserveDate;
	private String reserveTime;
	private String reserveFee;
	private String reserveType;
	private String name;
	private String phone;
	private String email;
	private Long userNo;
	private String rsrcNo;
	private String review;
	
	public Reserve toReserve() {
		return Reserve.builder()
				.rsrcNo(rsrcNo)
				.rsrcNm(rsrcNm)
				.userNo(userNo)
				.reserveDate(reserveDate)
				.reserveTime(reserveTime)
				.reserveType(reserveType)
				.reserveFee(reserveFee)
				.name(name)
				.phone(phone)
				.email(email)
				.useDate(useDate)
				.build();
	}
	
}