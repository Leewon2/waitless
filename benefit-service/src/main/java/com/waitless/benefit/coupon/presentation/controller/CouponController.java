package com.waitless.benefit.coupon.presentation.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.waitless.benefit.coupon.application.dto.CouponHistoryResponseDto;
import com.waitless.benefit.coupon.application.dto.CouponResponseDto;
import com.waitless.benefit.coupon.application.service.CouponHistoryService;
import com.waitless.benefit.coupon.application.service.CouponService;
import com.waitless.benefit.coupon.presentation.dto.CreateCouponRequestDto;
import com.waitless.benefit.coupon.presentation.dto.ReadCouponHistoriesRequestDto;
import com.waitless.benefit.coupon.presentation.dto.ReadCouponsRequestDto;
import com.waitless.benefit.coupon.presentation.mapper.CouponControllerMapper;
import com.waitless.common.aop.RoleCheck;
import com.waitless.common.domain.Role;
import com.waitless.common.exception.response.MultiResponse;
import com.waitless.common.exception.response.SingleResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

	private final CouponService couponService;
	private final CouponControllerMapper couponControllerMapper;
	private final CouponHistoryService couponHistoryService;

	// 쿠폰 생성
	@PostMapping
	@RoleCheck(roles = {Role.ADMIN})
	public ResponseEntity<SingleResponse<CouponResponseDto>> createCoupon(@RequestBody CreateCouponRequestDto createCouponRequestDto) {
		return ResponseEntity.ok(SingleResponse.success(couponService.generateCoupon(couponControllerMapper.toCreateCouponDto(createCouponRequestDto))));
	}

	// 쿠폰 단건 조회
	@GetMapping("/{id}")
	public ResponseEntity<SingleResponse<CouponResponseDto>> readCoupon(@PathVariable UUID id) {
		return ResponseEntity.ok(SingleResponse.success(couponService.findCoupon(id)));
	}

	// 쿠폰 전체 조회
	@GetMapping
	public ResponseEntity<?> readCoupons(
		@RequestParam(required = false) String title,
		@RequestParam(defaultValue = "1", required = false) int page,
		@RequestParam(defaultValue = "10", required = false) int size,
		@RequestParam(defaultValue = "DESC", required = false) Sort.Direction sortDirection,
		@RequestParam(defaultValue = "updatedAt", required = false) String sortBy
	) {
		ReadCouponsRequestDto readCouponsRequestDto = new ReadCouponsRequestDto(title, page, size, sortDirection, sortBy);
		Pageable pageable = PageRequest.of(page - 1, size);
		Page<CouponResponseDto> response = couponService.findAndSearchCoupons(couponControllerMapper.toReadCouponsDto(readCouponsRequestDto), pageable);
		return ResponseEntity.ok(MultiResponse.success(response));
	}

	// 쿠폰 수정
	@PatchMapping("/{id}")
	@RoleCheck(roles = {Role.ADMIN})
	public ResponseEntity<SingleResponse<CouponResponseDto>> updateCoupon(@PathVariable UUID id, @RequestBody Map<String, Object> updates) {
		CouponResponseDto couponResponseDto = couponService.modifyCoupon(id, updates);
		return ResponseEntity.ok(SingleResponse.success(couponResponseDto));
	}

	// 쿠폰 삭제
	@DeleteMapping("/{id}")
	@RoleCheck(roles = {Role.ADMIN})
	public ResponseEntity<Void> deleteCoupon(@PathVariable UUID id) {
		couponService.removeCoupon(id);
		return ResponseEntity.noContent().build();
	}

	// Coupon-Histroy --------------------------------------------------------------------------------------------

	// 쿠폰 받기
	@PostMapping("/issued/{couponId}")
	@RoleCheck(roles = {Role.USER})
	public ResponseEntity<SingleResponse<CouponHistoryResponseDto>> issuedCoupon(
		@PathVariable UUID couponId, @RequestHeader("X-User-Id") Long userId) {
		CouponHistoryResponseDto couponHistoryResponseDto = couponHistoryService.issuedCoupon(couponId, userId);
		return ResponseEntity.ok(SingleResponse.success(couponHistoryResponseDto));
	}

	// 쿠폰발급내역 단건 조회
	@GetMapping("/history/{id}")
	@RoleCheck(roles = {Role.ADMIN, Role.USER})
	public ResponseEntity<SingleResponse<CouponHistoryResponseDto>> readCouponHistory(@PathVariable UUID id) {
		CouponHistoryResponseDto couponHistoryResponseDto = couponHistoryService.findCouponHistory(id);
		return ResponseEntity.ok(SingleResponse.success(couponHistoryResponseDto));
	}

	// 쿠폰발급내역 목록 조회
	@GetMapping("/history")
	@RoleCheck(roles = {Role.ADMIN, Role.USER})
	public ResponseEntity<?> readCouponHistories(
		@RequestParam(required = false) String title,
		@RequestParam(defaultValue = "1", required = false) int page,
		@RequestParam(defaultValue = "10", required = false) int size,
		@RequestParam(defaultValue = "DESC", required = false) Sort.Direction sortDirection,
		@RequestParam(defaultValue = "updatedAt", required = false) String sortBy,
		@RequestHeader("X-User-Id") Long userId,
		@RequestHeader("X-User-Role") String roleHeader
	) {
		Role role = Role.valueOf(roleHeader.toUpperCase());
		ReadCouponHistoriesRequestDto readCouponHistoriesRequestDto = new ReadCouponHistoriesRequestDto(title, page, size, sortDirection, sortBy, userId, role);
		Pageable pageable = PageRequest.of(page - 1, size);
		Page<CouponHistoryResponseDto> response = couponHistoryService.findAndSearchCouponHistories(couponControllerMapper.toReadCouponHistoriesDto(readCouponHistoriesRequestDto), pageable);
		return ResponseEntity.ok(MultiResponse.success(response));
	}

	// 쿠폰발급내역 삭제
	@DeleteMapping("/history/{id}")
	@RoleCheck(roles = {Role.ADMIN, Role.USER})
	public ResponseEntity<Void> deleteCouponHistory(@PathVariable UUID id, @RequestHeader("X-User-Id") Long userId) {
		couponHistoryService.removeCouponHistory(id, userId);
		return ResponseEntity.noContent().build();
	}

	// 발급된 쿠폰 사용
	@PostMapping("/use/{id}")
	@RoleCheck(roles = {Role.USER})
	public ResponseEntity<String> useIssuedCoupon(@PathVariable UUID id, @RequestHeader("X-User-Id") Long userId) {
		couponHistoryService.useIssuedCoupon(id, userId);
		return ResponseEntity.ok("쿠폰 사용 완료");
	}

}
