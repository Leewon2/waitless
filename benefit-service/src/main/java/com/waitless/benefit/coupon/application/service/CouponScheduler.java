package com.waitless.benefit.coupon.application.service;

public interface CouponScheduler {

	void removeInvalidCoupons();

	void removeInvalidIssuedCoupons();
}
