package com.waitless.restaurant.restaurant.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.LocalTime;
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



    private OperatingHours(LocalTime openingTime, LocalTime closingTime) {
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public static OperatingHours of(LocalTime openingTime, LocalTime closingTime) {
        return new OperatingHours(openingTime, closingTime);
    }

    public void update(LocalTime openingTime, LocalTime closingTime) {
        if (openingTime != null) this.openingTime = openingTime;
        if (closingTime != null) this.closingTime = closingTime;
    }

    public boolean isOpenAt(LocalTime time) {
        if (openingTime.isBefore(closingTime)) {
            return !time.isBefore(openingTime) && !time.isAfter(closingTime);
        } else {
            // 자정이 넘어가는 영업 시간일 때
            return !time.isBefore(openingTime) || !time.isAfter(closingTime);
        }
    }

    public boolean isOpenNow() {
        return isOpenAt(LocalTime.now());
    }

}
