package com.runner.ddida.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class) // 변경될때 자동기록
@NoArgsConstructor
public class Reserve {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reserve_id")
	private Long reserveId;
	
	@Column(name = "user_no")
	private Long userNo;
	
	@Column(name = "rsrc_no")
	private String rsrcNo;
	
	@Column(name = "space_name")
	private String spaceName;
	
	@Column(name = "use_date")
	private String useDate;
	
	@Column(name = "reserve_date")
	private String reserveDate;
	
	@Column(name = "reserve_time")
	private String reserveTime;
	
	@Column(name = "reserve_fee")
	private int reserveFee;
	
	@Column(name = "reserve_type")
	private String reserveType;
	
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "email")
	private String email;
	
//	private String checkOut;
	
	@PrePersist
	public void onPrePersist() {
		this.reserveDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	    this.reserveTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
//	    this.checkOut = "이용 전";
	}
	
	@OneToMany(mappedBy = "reserve", cascade = CascadeType.ALL)
	private List<ReserveTime> reserveTimes;
	
	public void setReserveTimes(List<ReserveTime> reserveTimes) {
		this.reserveTimes = reserveTimes;
		for (ReserveTime reserveTime : reserveTimes) {
			reserveTime.setReserve(this);
		}
	}	
	
}

