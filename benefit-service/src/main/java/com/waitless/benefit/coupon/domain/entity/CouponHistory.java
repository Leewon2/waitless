package com.waitless.benefit.coupon.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_coupon_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted=false")
public class CouponHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private UUID id;

	@Column(nullable = false)
	private Long userId;

	@Column(nullable = false)
	private UUID couponId;

	@Column(nullable = false)
	private boolean isValid;

	@Column(nullable = false)
	private LocalDateTime expiredAt;

}
