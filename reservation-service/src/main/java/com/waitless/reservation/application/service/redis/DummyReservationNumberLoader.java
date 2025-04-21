//package com.waitless.reservation.application.service.redis;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class DummyReservationNumberLoader implements CommandLineRunner {
//
//    private final StringRedisTemplate redisTemplate;
//
//    @Override
//    public void run(String... args) {
//        String storeId = "d1f44111-b72c-4e0a-8c70-123456789abc";
//        String reservationNumberKey = "reservation:number:" + storeId;
//
//        redisTemplate.opsForValue().set(reservationNumberKey, "0");
//    }
//}