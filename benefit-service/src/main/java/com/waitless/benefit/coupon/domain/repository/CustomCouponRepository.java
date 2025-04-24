package com.waitless.benefit.coupon.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.waitless.benefit.coupon.domain.entity.Coupon;

public interface CustomCouponRepository {
	Page<Coupon> findAndSearchCoupons(String title, Sort.Direction sortDirection, String sortBy, Pageable pageable);

	List<Coupon> findInvalidCoupons();
}
