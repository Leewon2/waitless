package com.waitless.benefit.coupon.application.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.waitless.benefit.coupon.application.dto.CouponHistoryResponseDto;
import com.waitless.benefit.coupon.application.dto.ReadCouponHistoriesDto;

public interface CouponHistoryService {
	CouponHistoryResponseDto issuedCoupon(UUID couponId, Long userId);

	CouponHistoryResponseDto findCouponHistory(UUID id);

	Page<CouponHistoryResponseDto> findAndSearchCouponHistories(ReadCouponHistoriesDto readCouponHistoriesDto, Pageable pageable);

	void removeCouponHistory(UUID id, Long userId);

	void useIssuedCoupon(UUID id, Long userId);
}
