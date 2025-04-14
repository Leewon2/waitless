package com.waitless.benefit.coupon.application.service;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waitless.benefit.coupon.application.dto.CouponResponseDto;
import com.waitless.benefit.coupon.application.dto.CreateCouponDto;
import com.waitless.benefit.coupon.application.exception.CouponBusinessException;
import com.waitless.benefit.coupon.application.exception.CouponErrorCode;
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
	@Transactional
	public CouponResponseDto generateCoupon(CreateCouponDto createCouponDto) {
		Coupon coupon = Coupon.builder()
						.title(createCouponDto.title())
						.amount(createCouponDto.amount())
						.issuanceDate(createCouponDto.issuanceDate())
						.validPeriod(createCouponDto.validPeriod())
						.build();
		return couponServiceMapper.toCouponResponseDto(couponRepository.save(coupon));
	}

	// 쿠폰 단건 조회
	@Override
	public CouponResponseDto findCoupon(UUID id) {
		Coupon coupon = findCouponById(id);
		return couponServiceMapper.toCouponResponseDto(coupon);
	}

	private Coupon findCouponById(UUID id) {
		Coupon coupon = couponRepository.findById(id)
			.orElseThrow(()-> CouponBusinessException.from(CouponErrorCode.COUPON_NOT_FOUND));
		return coupon;
	}
}
