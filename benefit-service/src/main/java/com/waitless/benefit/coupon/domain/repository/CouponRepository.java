package com.waitless.benefit.coupon.domain.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.waitless.benefit.coupon.domain.entity.Coupon;

public interface CouponRepository {
	Coupon save(Coupon coupon);

	Optional<Coupon> findById(UUID id);
}
