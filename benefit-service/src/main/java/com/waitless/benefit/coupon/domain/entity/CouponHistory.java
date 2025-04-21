package com.waitless.benefit.coupon.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonProperty;
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
@Table(name = "p_coupon_history")
@NoArgsConstructor
@Where(clause = "is_deleted=false")
@Builder
public class CouponHistory extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private UUID id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private UUID couponId;

	@Column(nullable = false)
	private boolean isValid;

	@Column(nullable = false)
	private LocalDateTime expiredAt;

	public CouponHistory(UUID id, String title, Long userId, UUID couponId, boolean isValid, LocalDateTime expiredAt) {
		this.id = id;
		this.title = title;
		this.userId = userId;
		this.couponId = couponId;
		this.isValid = isValid;
		this.expiredAt = expiredAt;
	}

	public void used() {
		this.isValid = false;
	}
}
