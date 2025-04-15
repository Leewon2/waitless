package com.waitless.benefit.coupon.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.waitless.benefit.coupon.application.dto.CouponHistoryResponseDto;
import com.waitless.benefit.coupon.application.dto.CouponResponseDto;
import com.waitless.benefit.coupon.application.dto.ReadCouponHistoriesDto;
import com.waitless.benefit.coupon.application.exception.CouponBusinessException;
import com.waitless.benefit.coupon.application.exception.CouponErrorCode;
import com.waitless.benefit.coupon.application.mapper.CouponHistoryServiceMapper;
import com.waitless.benefit.coupon.domain.entity.CouponHistory;
import com.waitless.benefit.coupon.domain.repository.CouponHistoryRepository;
import com.waitless.benefit.coupon.infrastructure.repository.CustomCouponHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponHistoryServiceImpl implements CouponHistoryService{

	private final CouponHistoryRepository couponHistoryRepository;
	private final CustomCouponHistoryRepository customCouponHistoryRepository;
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
			.isValid(true)
			.expiredAt(expiredDate)
			.build();
		// 쿠폰 발급가능수량 -1 차감
		couponService.decreaseCouponAmount(couponId);
		return couponHistoryServiceMapper.toCouponHistoryResponseDto(couponHistoryRepository.save(couponHistory));
	}

	// 쿠폰발급내역 단건 조회
	@Override
	public CouponHistoryResponseDto findCouponHistory(UUID id) {
		CouponHistory couponHistory = findCouponHistoryById(id);
		return couponHistoryServiceMapper.toCouponHistoryResponseDto(couponHistory);
	}

	// 쿠폰발급내역 목록 조회 + 검색
	@Override
	public Page<CouponHistoryResponseDto> findAndSearchCouponHistories(ReadCouponHistoriesDto readCouponHistoriesDto, Pageable pageable) {
		Page<CouponHistory> couponHistoryList = customCouponHistoryRepository.findAndSearchCouponHistories(
			readCouponHistoriesDto.title(),
			readCouponHistoriesDto.sortDirection(),
			readCouponHistoriesDto.sortBy(),
			readCouponHistoriesDto.userId(),
			pageable
		);
		List<CouponHistoryResponseDto> dtoList = couponHistoryList
			.stream()
			.map(couponHistoryServiceMapper::toCouponHistoryResponseDto)
			.toList();
		return new PageImpl<>(dtoList, pageable, couponHistoryList.getTotalElements());
	}

	// 쿠폰발급내역 삭제
	@Override
	@Transactional
	public void removeCouponHistory(UUID id, String userId) {
		CouponHistory couponHistory = findCouponHistoryById(id);
		if (!couponHistory.getUserId().equals(Long.parseLong(userId))) {
			throw CouponBusinessException.from(CouponErrorCode.COUPONHISTORY_UNAUTHORIZED);
		}
		couponHistory.delete();
	}

	// 만료된 쿠폰발급내역 자동삭제 - 이미 사용했거나, 사용가능일자를 지난 경우
	// @Scheduled(cron = "*/10 * * * * *") // 10초마다
	@Transactional
	public void removeInvalidIssuedCoupons() {
		List<CouponHistory> invalidIssuedCoupons = customCouponHistoryRepository.findInvalidIssuedCoupons();
		for (CouponHistory coupon : invalidIssuedCoupons) {
			coupon.delete();
		}
	}

	private CouponHistory findCouponHistoryById(UUID id) {
		CouponHistory couponHistory = couponHistoryRepository.findById(id)
			.orElseThrow(()-> CouponBusinessException.from(CouponErrorCode.COUPONHISTORY_NOT_FOUND));
		return couponHistory;
	}

}
