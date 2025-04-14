package com.waitless.benefit.coupon.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.waitless.benefit.coupon.domain.entity.Coupon;

public interface CouponRepository {
	Coupon save(Coupon coupon);

	Optional<Coupon> findById(UUID id);
}
