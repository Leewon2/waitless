package com.waitless.benefit.coupon.presentation.controller;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waitless.benefit.coupon.application.dto.CouponResponseDto;
import com.waitless.benefit.coupon.application.service.CouponService;
import com.waitless.benefit.coupon.domain.entity.Coupon;
import com.waitless.benefit.coupon.presentation.dto.CreateCouponRequestDto;
import com.waitless.benefit.coupon.presentation.mapper.CouponControllerMapper;
import com.waitless.common.exception.response.SingleResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

	private final CouponService couponService;
	private final CouponControllerMapper couponControllerMapper;

	// 쿠폰 생성
	@PostMapping
	public ResponseEntity<SingleResponse<CouponResponseDto>> createCoupon(@RequestBody CreateCouponRequestDto createCouponRequestDto) {
		return ResponseEntity.ok(SingleResponse.success(couponService.generateCoupon(couponControllerMapper.toCreateCouponDto(createCouponRequestDto))));
	}

	// 쿠폰 단건 조회
	@GetMapping("/{id}")
	public ResponseEntity<SingleResponse<CouponResponseDto>> readCoupon(@PathVariable UUID id) {
		return ResponseEntity.ok(SingleResponse.success(couponService.findCoupon(id)));
	}
}
