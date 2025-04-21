//package com.waitless.reservation.application.service.redis;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// *  초기 예약 생성을 위한 임시로 redis에 식당에 메뉴 재고 및 예약 제한 정보 넣는 로직
// */
//@Component
//@RequiredArgsConstructor
//public class DummyStockLoader implements CommandLineRunner {
//
//    private final StringRedisTemplate redisTemplate;
//
//    @Override
//    public void run(String... args) {
//        Map<String, String> stockMap = new HashMap<>();
//
//        String storeId = "d1f44111-b72c-4e0a-8c70-123456789abc";
//        String menuId1 = "a1111111-bbbb-4ccc-dddd-123456789abc";
//        String menuId2 = "b2222222-eeee-4fff-aaaa-987654321def";
//
//        // 메뉴 재고 초기값
//        stockMap.put("stock:" + storeId + ":" + menuId1, "10000");
//        stockMap.put("stock:" + storeId + ":" + menuId2, "10000");
//
//        // 예약 팀 수 제한 관련
//        stockMap.put("reservation:teamLimit:" + storeId, "3"); // 하루 최대 100팀
//        stockMap.put("reservation:teamCount:" + storeId, "0");   // 현재 예약된 팀 수
//
//        stockMap.forEach((key, value) -> redisTemplate.opsForValue().set(key, value));
//    }
//}