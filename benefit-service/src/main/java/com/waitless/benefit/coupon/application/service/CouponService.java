package com.waitless.benefit.coupon.application.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.waitless.benefit.coupon.application.dto.CouponResponseDto;
import com.waitless.benefit.coupon.application.dto.CreateCouponDto;
import com.waitless.benefit.coupon.application.dto.ReadCouponsDto;

public interface CouponService {
	CouponResponseDto generateCoupon(CreateCouponDto createCouponDto);

	CouponResponseDto findCoupon(UUID id);

	Page<CouponResponseDto> findAndSearchCoupons(ReadCouponsDto readCouponsDto, Pageable pageable);
}
