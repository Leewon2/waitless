// package com.waitless.benefit.coupon.application.service;
//
// import static org.assertj.core.api.Assertions.*;
//
// import java.util.Optional;
// import java.util.UUID;
// import java.util.concurrent.TimeUnit;
//
// import org.apache.kafka.clients.producer.ProducerRecord;
// import org.awaitility.Awaitility;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.kafka.core.KafkaTemplate;
// import org.springframework.kafka.test.context.EmbeddedKafka;
//
// import com.waitless.benefit.coupon.domain.entity.CouponHistory;
// import com.waitless.benefit.coupon.domain.repository.CouponHistoryRepository;
// import com.waitless.common.event.CouponUsedEvent;
//
// @SpringBootTest
// @EmbeddedKafka(partitions = 1, topics = {"coupon-used-events"})
// public class CouponKafkaTest {
//
// 	@Autowired
// 	private KafkaTemplate<String, CouponUsedEvent> kafkaTemplate;
//
// 	@Autowired
// 	private CouponHistoryRepository couponHistoryRepository;
//
// 	@Test
// 	void testSendMessage() {
// 		CouponUsedEvent event = new CouponUsedEvent(UUID.fromString("d56da74b-bef3-4f87-9ade-426d800ef36e"), 1L);
//
// 		Optional<CouponHistory> before = couponHistoryRepository.findById(event.getCouponHistoryId());
// 		System.out.println("이벤트 발행 전 : " + before.get().isValid());
// 		kafkaTemplate.send(new ProducerRecord<>("coupon-used-events", UUID.randomUUID().toString(), event));
//
// 		Awaitility.await().atMost(20, TimeUnit.SECONDS).untilAsserted(() -> {
// 			Optional<CouponHistory> after = couponHistoryRepository.findById(event.getCouponHistoryId());
// 			System.out.println("이벤트 발행 후 : " + after.get().isValid());
// 			assertThat(after.get().isValid()).isFalse();
// 		});
// 	}
//
// }
