package com.waitless.benefit.coupon.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.Where;

import com.waitless.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted=false")
@Builder
public class Coupon extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private UUID id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private int amount;

	@Column(nullable = false)
	private LocalDateTime issuanceDate;

	@Column(nullable = false)
	private int validPeriod;

	public Coupon(UUID id, String title, int amount, LocalDateTime issuanceDate, int validPeriod) {
		this.id = id;
		this.title = title;
		this.amount = amount;
		this.issuanceDate = issuanceDate;
		this.validPeriod = validPeriod;
	}

	public void modifyCouponInfo(String key, Object value) {
		switch (key) {
			case "title" -> this.title = (String) value;
			case "amount" -> this.amount = (int) value;
			case "issuanceDate" -> this.issuanceDate = (LocalDateTime) value;
			case "validPeriod" -> this.validPeriod = (int) value;
		}
	}

	public void decrease() {
		this.amount = this.amount - 1;
	}
}
