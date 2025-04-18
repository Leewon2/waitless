package com.waitless.benefit.coupon.application.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.waitless.benefit.coupon.application.dto.CouponHistoryResponseDto;
import com.waitless.benefit.coupon.application.dto.CouponResponseDto;
import com.waitless.benefit.coupon.application.dto.CreateCouponDto;
import com.waitless.benefit.coupon.application.exception.CouponBusinessException;
import com.waitless.benefit.coupon.application.exception.CouponErrorCode;
import com.waitless.benefit.coupon.domain.entity.Coupon;
import com.waitless.benefit.coupon.domain.repository.CouponHistoryRepository;
import com.waitless.benefit.coupon.domain.repository.CouponRepository;

import jakarta.transaction.Transactional;
import scala.Option;

@SpringBootTest
class CouponHistoryServiceImplTest {
	@Autowired
	private CouponHistoryService couponHistoryService;

	@Autowired
	private CouponRepository couponRepository;

	@Autowired
	private CouponService couponService;

	@Autowired
	private RedissonClient redissonClient;

	private UUID identifier = UUID.randomUUID();

	@RepeatedTest(50)
	@Execution(ExecutionMode.CONCURRENT)
	@Transactional
	void testConcurrentCouponHistory() throws InterruptedException {
		CouponResponseDto couponResponseDto = couponService.generateCoupon(new CreateCouponDto(
			"20% 할인 쿠폰", 10, LocalDateTime.of(2025, 4, 30, 18, 0), 14));

		final UUID couponId = couponResponseDto.id();

		Long userId = 123123L;

		int threadCount = 50;
		boolean isLocked = false;
		String LOCK_KEY = "LOCK:CH:" + identifier;
		RLock lock = redissonClient.getLock(LOCK_KEY);
		try {
			isLocked = lock.tryLock(5, 3, TimeUnit.SECONDS);
			Coupon coupon = couponRepository.findById(couponId).orElseThrow(()-> CouponBusinessException.from(
				CouponErrorCode.COUPON_NOT_FOUND));
			coupon.decrease();
			couponRepository.save(coupon);

			if (coupon.getAmount() <= 0) {
				throw CouponBusinessException.from(CouponErrorCode.COUPON_AMOUNT_EXHAUSTED);
			}

			System.out.println("coupon.getAmount() = " + coupon.getAmount());
			if (isLocked) {
				System.out.println("락 획득 성공!");
				Thread.sleep(1000);
			} else {
				System.out.println("락 획득 실패");
			}
		} finally {
			if (isLocked && lock.isHeldByCurrentThread()) {
				lock.unlock();
				System.out.println("락이 해제됨!");
			}
		}
	}
}
	// 	ExecutorService executor = Executors.newFixedThreadPool(threadCount);
	// 	CountDownLatch latch = new CountDownLatch(threadCount);
	// 	List<Future<CouponHistoryResponseDto>> results = new ArrayList<>();
	// 	for (int i = 0; i < threadCount; i++) {
	// 		results.add(executor.submit(()->{
	// 			try{
	// 				return couponHistoryService.issuedCoupon(couponId, userId);
	// 			}finally {
	// 				latch.countDown();
	// 			}
	// 		}));
	// 	}
	// 	latch.await();
	//
	// 	for (Future<CouponHistoryResponseDto> result : results) {
	// 		try {
	// 			System.out.println("발급 성공: " + result.get());
	// 		} catch (Exception e) {
	// 			System.out.println("발급 실패: " + e.getMessage());
	// 		}
	// 	}
	// 	executor.shutdown();
	// }
