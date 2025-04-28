package com.waitless.payment.application.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.waitless.payment.application.dto.CouponHistoryInfoDto;

@FeignClient(name = "benefit-service", path = "/api")
public interface BenefitServiceClient {

	@GetMapping("/coupons/{couponId}")
	CouponHistoryInfoDto readCoupon(@PathVariable UUID couponId);
}
