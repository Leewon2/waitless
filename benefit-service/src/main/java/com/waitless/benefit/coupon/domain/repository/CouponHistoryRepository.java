package com.waitless.benefit.coupon.domain.repository;

import com.waitless.benefit.coupon.domain.entity.CouponHistory;

public interface CouponHistoryRepository {
	CouponHistory save(CouponHistory couponHistory);
}
