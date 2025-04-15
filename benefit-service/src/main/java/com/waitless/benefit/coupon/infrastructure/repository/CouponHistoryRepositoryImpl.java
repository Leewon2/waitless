package com.waitless.benefit.coupon.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.waitless.benefit.coupon.domain.entity.CouponHistory;
import com.waitless.benefit.coupon.domain.repository.CouponHistoryRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CouponHistoryRepositoryImpl implements CouponHistoryRepository {

	private final JpaCouponHistoryRepository jpaCouponHistoryRepository;

	@Override
	public CouponHistory save(CouponHistory couponHistory) {
		return jpaCouponHistoryRepository.save(couponHistory);
	}

	@Override
	public Optional<CouponHistory> findById(UUID id) {
		return jpaCouponHistoryRepository.findById(id);
	}
}
