package com.waitless.benefit.coupon.application.service;

import org.springframework.stereotype.Service;

import com.waitless.benefit.coupon.application.dto.CouponResponseDto;
import com.waitless.benefit.coupon.application.dto.CreateCouponDto;
import com.waitless.benefit.coupon.application.mapper.CouponServiceMapper;
import com.waitless.benefit.coupon.domain.entity.Coupon;
import com.waitless.benefit.coupon.domain.repository.CouponRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

	private final CouponServiceMapper couponServiceMapper;
	private final CouponRepository couponRepository;

	// 쿠폰 생성
	@Override
	public CouponResponseDto generateCoupon(CreateCouponDto createCouponDto) {
		Coupon coupon = Coupon.builder()
						.title(createCouponDto.title())
						.amount(createCouponDto.amount())
						.issuanceDate(createCouponDto.issuanceDate())
						.validPeriod(createCouponDto.validPeriod())
						.build();
		return couponServiceMapper.toCouponResponseDto(couponRepository.save(coupon));
	}
}
