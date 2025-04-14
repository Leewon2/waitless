package com.waitless.benefit.coupon.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.waitless.benefit.coupon.application.dto.CouponResponseDto;
import com.waitless.benefit.coupon.domain.entity.Coupon;
import com.waitless.benefit.coupon.domain.repository.CouponRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

	private final JpaCouponRepository jpaCouponRepository;

	@Override
	public Coupon save(Coupon coupon) {
		return jpaCouponRepository.save(coupon);
	}

	@Override
	public Optional<Coupon> findById(UUID id) {
		return jpaCouponRepository.findById(id);
	}
}
