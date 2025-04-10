package com.waitless.restaurant.restaurant.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor
public class OperatingHours {

    @Column(name = "opening_time", nullable = false)
    private LocalTime openingTime;

    @Column(name = "closing_time", nullable = false)
    private LocalTime closingTime;



    @Builder
    public OperatingHours(LocalTime openingTime, LocalTime closingTime) {
        if (!openingTime.isBefore(closingTime)) {
            throw new IllegalArgumentException("영업 시작 시간은 종료 시간보다 이전이어야 합니다.");
        }
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public boolean isOpenAt(LocalTime time) {
        return !time.isBefore(openingTime) && !time.isAfter(closingTime);
    }


    public boolean isOpenNow() {
        return isOpenAt(LocalTime.now());
    }

}
