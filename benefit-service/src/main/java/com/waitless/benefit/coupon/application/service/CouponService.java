package com.waitless.benefit.coupon.application.service;

import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.waitless.benefit.coupon.application.dto.CouponResponseDto;
import com.waitless.benefit.coupon.application.dto.CreateCouponDto;
import com.waitless.benefit.coupon.application.dto.ReadCouponsDto;
import com.waitless.benefit.coupon.domain.entity.Coupon;

public interface CouponService {
	CouponResponseDto generateCoupon(CreateCouponDto createCouponDto);

	CouponResponseDto findCoupon(UUID id);

	Page<CouponResponseDto> findAndSearchCoupons(ReadCouponsDto readCouponsDto, Pageable pageable);

	CouponResponseDto modifyCoupon(UUID id, Map<String, Object> updates);

	void removeCoupon(UUID id);

	Coupon decreaseCouponAmount(UUID id);
}
