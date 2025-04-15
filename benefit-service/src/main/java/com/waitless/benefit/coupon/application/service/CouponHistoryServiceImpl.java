package com.waitless.benefit.coupon.application.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waitless.benefit.coupon.application.dto.CouponHistoryResponseDto;
import com.waitless.benefit.coupon.application.dto.CouponResponseDto;
import com.waitless.benefit.coupon.application.exception.CouponBusinessException;
import com.waitless.benefit.coupon.application.exception.CouponErrorCode;
import com.waitless.benefit.coupon.application.mapper.CouponHistoryServiceMapper;
import com.waitless.benefit.coupon.domain.entity.CouponHistory;
import com.waitless.benefit.coupon.domain.repository.CouponHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponHistoryServiceImpl implements CouponHistoryService{

	private final CouponHistoryRepository couponHistoryRepository;
	private final CouponHistoryServiceMapper couponHistoryServiceMapper;
	private final CouponService couponService;

	// 쿠폰 받기
	@Override
	@Transactional
	public CouponHistoryResponseDto issuedCoupon(UUID couponId, String userId) { // redisson 분산 락...
		CouponResponseDto couponInfo = couponService.findCoupon(couponId);
		LocalDateTime today = LocalDateTime.now();
		// 쿠폰 발급 가능일자가 지나면 예외처리
		if (!today.isBefore(couponInfo.issuanceDate())) {
			throw CouponBusinessException.from(CouponErrorCode.COUPON_ISSUED_IMPOSSIBLE);
		}
		// 쿠폰 사용 가능 일자
		LocalDateTime expiredDate = today.plusDays(couponInfo.validPeriod());
		CouponHistory couponHistory = CouponHistory.builder()
			.title(couponInfo.title())
			.couponId(couponId)
			.userId(Long.parseLong(userId))
			.expiredAt(expiredDate)
			.build();
		// 쿠폰 발급가능수량 -1 차감
		couponService.decreaseCouponAmount(couponId);
		return couponHistoryServiceMapper.toCouponHistoryResponseDto(couponHistoryRepository.save(couponHistory));
	}
}
