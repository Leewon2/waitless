package com.waitless.benefit.coupon.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waitless.benefit.coupon.domain.entity.CouponHistory;

public interface JpaCouponHistoryRepository extends JpaRepository<CouponHistory, UUID> {
}
