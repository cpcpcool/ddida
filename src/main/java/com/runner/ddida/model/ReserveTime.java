package com.runner.ddida.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class ReserveTime {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reserve_time_no")
	Long reserveTimeNo;
	
	@Column(name = "use_time")
	String useTime;
	
	@ManyToOne
    @JoinColumn(name = "reserve_id", referencedColumnName = "reserve_id")
    private Reserve reserve;
}
