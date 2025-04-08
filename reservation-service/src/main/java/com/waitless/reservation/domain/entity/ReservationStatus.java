package com.waitless.reservation.domain.entity;

public enum ReservationStatus {
    WAITING, // 웨이팅 중
    COMPLETED, // 방문 완료
    CANCELLED, // 예약 취소
    NO_SHOW // 노쇼
}
