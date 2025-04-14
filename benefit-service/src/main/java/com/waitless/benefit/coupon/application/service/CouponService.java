package com.waitless.benefit.coupon.application.service;

import com.waitless.benefit.coupon.application.dto.CouponResponseDto;
import com.waitless.benefit.coupon.application.dto.CreateCouponDto;

public interface CouponService {
	CouponResponseDto generateCoupon(CreateCouponDto createCouponDto);
}
