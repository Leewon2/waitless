package com.waitless.benefit.coupon.infrastructure.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.waitless.benefit.coupon.application.service.CouponScheduler;
import com.waitless.benefit.coupon.domain.entity.Coupon;
import com.waitless.benefit.coupon.domain.entity.CouponHistory;
import com.waitless.benefit.coupon.domain.repository.CustomCouponHistoryRepository;
import com.waitless.benefit.coupon.domain.repository.CustomCouponRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CouponSchedulerImpl implements CouponScheduler {

	private final CustomCouponRepository customCouponRepository;
	private final CustomCouponHistoryRepository customCouponHistoryRepository;

	// 쿠폰발급가능일자 지난 쿠폰들 자동삭제
	@Override
	// @Scheduled(cron = "*/10 * * * * *") // 10초마다
	@Transactional
	public void removeInvalidCoupons() {
		List<Coupon> invalidCoupons = customCouponRepository.findInvalidCoupons();
		for (Coupon coupon : invalidCoupons) {
			coupon.delete();
		}
	}

	// 만료된 쿠폰발급내역 자동삭제 - 이미 사용했거나, 사용가능일자를 지난 경우
	@Override
	// @Scheduled(cron = "*/10 * * * * *") // 10초마다
	@Transactional
	public void removeInvalidIssuedCoupons() {
		List<CouponHistory> invalidIssuedCoupons = customCouponHistoryRepository.findInvalidIssuedCoupons();
		for (CouponHistory coupon : invalidIssuedCoupons) {
			coupon.delete();
		}
	}

}
