package com.waitless.benefit.coupon.infrastructure.adaptor.in;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.waitless.benefit.coupon.application.exception.CouponBusinessException;
import com.waitless.benefit.coupon.application.service.CouponHistoryService;
import com.waitless.common.event.CouponUsedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponUsedEventConsumer {

	private final CouponHistoryService couponHistoryService;

	@KafkaListener(topics = "coupon-used-events", groupId = "coupon-service", containerFactory = "kafkaListenerContainerFactory")
	public void handleCouponUsedEvent(CouponUsedEvent event) {
		log.info("[Kafka] 쿠폰 사용 완료 : couponHistoryId={}, userId={}", event.getCouponHistoryId(), event.getUserId());
		try {
			couponHistoryService.useIssuedCoupon(event.getCouponHistoryId(), event.getUserId());
		} catch (CouponBusinessException e) {
			log.info("[Kafka] 이벤트 발행 실패 {}", e.getMessage());
		}
	}
}
