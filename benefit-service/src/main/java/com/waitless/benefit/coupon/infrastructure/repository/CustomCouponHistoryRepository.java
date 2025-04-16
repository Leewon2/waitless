package com.waitless.benefit.coupon.infrastructure.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.waitless.benefit.coupon.domain.entity.CouponHistory;

public interface CustomCouponHistoryRepository {
	Page<CouponHistory> findAndSearchCouponHistories(String title, Sort.Direction sortDirection, String sortBy, Long userId, Pageable pageable);

	List<CouponHistory> findInvalidIssuedCoupons();
}
