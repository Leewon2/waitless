package com.waitless.benefit.coupon.infrastructure.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import com.waitless.benefit.coupon.domain.entity.Coupon;

import jakarta.persistence.LockModeType;

public interface JpaCouponRepository extends JpaRepository<Coupon, UUID> {
}
