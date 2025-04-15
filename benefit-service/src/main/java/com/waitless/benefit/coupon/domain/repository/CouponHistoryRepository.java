package com.waitless.benefit.coupon.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.waitless.benefit.coupon.domain.entity.CouponHistory;

public interface CouponHistoryRepository {
	CouponHistory save(CouponHistory couponHistory);

	Optional<CouponHistory> findById(UUID id);
}
