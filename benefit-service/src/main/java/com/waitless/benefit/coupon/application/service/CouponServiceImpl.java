package com.waitless.benefit.coupon.application.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waitless.benefit.coupon.application.dto.CouponCacheDto;
import com.waitless.benefit.coupon.application.dto.CouponResponseDto;
import com.waitless.benefit.coupon.application.dto.CreateCouponDto;
import com.waitless.benefit.coupon.application.dto.ReadCouponsDto;
import com.waitless.benefit.coupon.application.exception.CouponBusinessException;
import com.waitless.benefit.coupon.application.exception.CouponErrorCode;
import com.waitless.benefit.coupon.application.mapper.CouponServiceMapper;
import com.waitless.benefit.coupon.domain.entity.Coupon;
import com.waitless.benefit.coupon.domain.repository.CouponRepository;
import com.waitless.benefit.coupon.domain.repository.CustomCouponRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

	private final CouponServiceMapper couponServiceMapper;
	private final CouponRepository couponRepository;
	private final CustomCouponRepository customCouponRepository;
	private final RedisCacheService redisCacheService;

	// 쿠폰 생성
	@Override
	@Transactional
	public CouponResponseDto generateCoupon(CreateCouponDto createCouponDto) {
		Coupon coupon = couponRepository.save(
			Coupon.builder()
				.title(createCouponDto.title())
				.amount(createCouponDto.amount())
				.issuanceDate(createCouponDto.issuanceDate())
				.validPeriod(createCouponDto.validPeriod())
				.build()
		);
		redisCacheService.cacheCoupon(coupon);
		return couponServiceMapper.toCouponResponseDto(coupon);
	}

	// 쿠폰 단건 조회
	@Override
	public CouponResponseDto findCoupon(UUID id) {
		CouponCacheDto coupon = redisCacheService.getCoupon(id);
		return couponServiceMapper.toCouponResponseDto(coupon);
	}

	// 쿠폰 전체 조회 + 검색
	@Override
	public Page<CouponResponseDto> findAndSearchCoupons(ReadCouponsDto readCouponsDto, Pageable pageable) {
		Page<Coupon> couponList = customCouponRepository.findAndSearchCoupons(readCouponsDto.title(), readCouponsDto.sortDirection(), readCouponsDto.sortBy(), pageable);
		List<CouponResponseDto> dtoList = couponList
			.stream()
			.map(couponServiceMapper::toCouponResponseDto)
			.toList();
		return new PageImpl<>(dtoList, pageable, couponList.getTotalElements());
	}

	// 쿠폰 수정
	@Override
	@Transactional
	public CouponResponseDto modifyCoupon(UUID id, Map<String, Object> updates) {
		Coupon coupon = findCouponById(id);
		updates.forEach((key, value) -> coupon.modifyCouponInfo(key, value));
		redisCacheService.cacheCoupon(coupon);
		return couponServiceMapper.toCouponResponseDto(coupon);
	}

	// 쿠폰 삭제
	@Override
	@Transactional
	public void removeCoupon(UUID id) {
		Coupon coupon = findCouponById(id);
		coupon.delete();
	}

	// 쿠폰 수량 감소
	@Override
	@Transactional
	public Coupon decreaseCouponAmount(UUID id) {
		Coupon coupon = findCouponById(id);
		if (coupon.getAmount() <= 0) {
			throw CouponBusinessException.from(CouponErrorCode.COUPON_AMOUNT_EXHAUSTED);
		}
		coupon.decrease();
		redisCacheService.cacheCoupon(coupon);
		return coupon;
	}

	// 쿠폰 단건 조회(DB)
	private Coupon findCouponById(UUID id) {
		Coupon coupon = couponRepository.findById(id)
			.orElseThrow(() -> CouponBusinessException.from(CouponErrorCode.COUPON_NOT_FOUND));
		return coupon;
	}
}
