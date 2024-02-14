package com.runner.ddida.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.runner.ddida.dto.ReserveDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class) // 변경될때 자동기록
@NoArgsConstructor
@Table(name = "reserve")
@AllArgsConstructor
@Builder
public class Reserve {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reserve_id")
	private Long reserveId;
	
	@Column(name = "user_no")
	@JsonIgnore
	private Long userNo;
	
	@Column(name = "rsrc_no")
	private String rsrcNo;
	
	@Column(name = "rsrc_nm")
	private String rsrcNm;
	
	@Column(name = "use_date")
	private String useDate;
	
	@Column(name = "reserve_date")
	private String reserveDate;
	
	@Column(name = "reserve_time")
	private String reserveTime;
	
	@Column(name = "reserve_fee")
	private String reserveFee;
		
	@Column(name = "reserve_type")
	private String reserveType;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "checkout")
	private Integer checkout;
	
	@Column(name = "review")
	private String review;
	
	@PrePersist
	public void onPrePersist() {
		this.reserveDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	    this.reserveTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	    this.reserveType = "업체 문의";
	}
	
	@OneToMany(mappedBy = "reserve", cascade = CascadeType.ALL)
	private List<ReserveTime> reserveTimes;
	
	public void setReserveTimes(List<ReserveTime> reserveTimes) {
		this.reserveTimes = reserveTimes;
		for (ReserveTime reserveTime : reserveTimes) {
			reserveTime.setReserve(this);
		}
	}		
	
	public ReserveDto toReserveDto() {
		return ReserveDto.builder()
				.rsrcNo(rsrcNo)
				.rsrcNm(rsrcNm)
				.userNo(userNo)
				.name(name)
				.phone(phone)
				.email(email)
				.review(review)
				.reserveType(reserveType)
				.reserveFee(reserveFee)
				.checkout(0)
				.build();
	}
	
}

