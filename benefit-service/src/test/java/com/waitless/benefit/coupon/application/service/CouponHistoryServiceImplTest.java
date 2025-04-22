// package com.waitless.benefit.coupon.application.service;
//
// import java.time.LocalDateTime;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.UUID;
// import java.util.concurrent.CountDownLatch;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;
// import java.util.concurrent.Future;
//
// import org.junit.jupiter.api.Test;
// import org.redisson.api.RedissonClient;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
//
// import com.waitless.benefit.coupon.application.dto.CouponHistoryResponseDto;
// import com.waitless.benefit.coupon.application.dto.CouponResponseDto;
// import com.waitless.benefit.coupon.application.dto.CreateCouponDto;
// import com.waitless.benefit.coupon.domain.repository.CouponRepository;
// @SpringBootTest
// class CouponHistoryServiceImplTest {
// 	@Autowired
// 	private CouponHistoryService couponHistoryService;
// 	@Autowired
// 	private CouponService couponService;
// 	@Autowired
// 	private RedissonClient redissonClient;
// 	@Autowired
// 	private CouponRepository couponRepository;
//
// 	@Test
// 	void testConcurrentCouponIssueWithDistributedLock() throws InterruptedException {
// 		// 1. 쿠폰 생성
// 		CouponResponseDto couponResponseDto = couponService.generateCoupon(new CreateCouponDto(
// 			"20% 할인 쿠폰", 10, LocalDateTime.of(2025, 4, 30, 18, 0), 14
// 		));
// 		UUID couponId = couponResponseDto.id();
// 		Long userId = 123123L;
// 		// 2. 동시성 테스트 설정
// 		int threadCount = 50;
// 		ExecutorService executor = Executors.newFixedThreadPool(threadCount);
// 		CountDownLatch latch = new CountDownLatch(threadCount);
// 		List<Future<CouponHistoryResponseDto>> results = new ArrayList<>();
// 		for (int i = 0; i < threadCount; i++) {
// 			results.add(executor.submit(()->{
// 				try{
// 					return couponHistoryService.issuedCoupon(couponId, userId);
// 				}finally {
// 					latch.countDown();
// 				}
// 			}));
// 		}
// 		latch.await();
//
// 		for (Future<CouponHistoryResponseDto> result : results) {
// 			try {
// 				System.out.println("발급 성공: " + result.get());
// 			} catch (Exception e) {
// 				System.out.println("발급 실패: " + e.getMessage());
// 			}
// 		}
// 		executor.shutdown();
// 	}
// }

// ExecutorService executor = Executors.newFixedThreadPool(threadCount);
// CountDownLatch latch = new CountDownLatch(threadCount);
// List<Future<String>> results = new ArrayList<>();
// for (int i = 0; i < threadCount; i++) {
// 	results.add(executor.submit(() -> {
// 		String lockKey = "LOCK:CH:" + couponId;
// 		RLock lock = redissonClient.getLock(lockKey);
// 		boolean isLocked = false;
// 		try {
// 			isLocked = lock.tryLock(5, 3, TimeUnit.SECONDS);
// 			if (isLocked) {
// 				Coupon coupon = couponRepository.findById(couponId).orElseThrow(()-> CouponBusinessException.from(CouponErrorCode.COUPON_NOT_FOUND));
// 				coupon.decrease();
// 				couponRepository.save(coupon);
// 				if (coupon.getAmount() <= 0) {
// 					throw CouponBusinessException.from(CouponErrorCode.COUPON_AMOUNT_EXHAUSTED);
// 				}
// 				return "발급 성공: " ;//+ issued;
// 			} else {
// 				return "락 획득 실패";
// 			}
// 		} catch (CouponBusinessException e) {
// 			return "쿠폰 예외: " + e.getErrorCode();
// 		} catch (Exception e) {
// 			return "기타 예외: " + e.getMessage();
// 		} finally {
// 			if (isLocked && lock.isHeldByCurrentThread()) {
// 				lock.unlock();
// 			}
// 			latch.countDown();
// 		}
// 	}));
// }
// latch.await();
// executor.shutdown();
// for (Future<String> result : results) {
// 	try {
// 		System.out.println(result.get());
// 	} catch (Exception e) {
// 		System.out.println("결과 예외: " + e.getMessage());
// 	}
// }