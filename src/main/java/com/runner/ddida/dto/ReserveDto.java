package com.runner.ddida.dto;

import com.runner.ddida.model.Reserve;

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
	private String reserveFee;
	private String reserveType;
	private String name;
	private String phone;
	private String email;
	private Long userNo;
	private String rsrcNo;
	private String review;
	
	public Reserve toReserveDto() {
		return Reserve.builder()
				.rsrcNo(rsrcNo)
				.rsrcNm(rsrcNm)
				.userNo(userNo)
				.reserveType(reserveType)
				.reserveFee(reserveFee)
				.name(name)
				.phone(phone)
				.email(email)
				.useDate(useDate)
				.build();
	}
}
