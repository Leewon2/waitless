package com.waitless.restaurant.restaurant.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Location {

    @Column(name = "latitude", precision = 9, scale = 6)
    private Double latitude;

    @Column(name = "longitude", precision = 9, scale = 6)
    private Double longitude;
}
