package com.waitless.restaurant.restaurant.domain.entity;

import com.waitless.common.domain.BaseTimeEntity;
import com.waitless.restaurant.restaurant.application.exception.RestaurantBusinessException;
import com.waitless.restaurant.restaurant.application.exception.RestaurantErrorCode;
import com.waitless.restaurant.restaurant.domain.vo.Location;
import com.waitless.restaurant.restaurant.domain.vo.OperatingHours;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Getter
@Table(name="p_restaurant")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted = false")
public class Restaurant extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long ownerId;

    @Column(length = 20, nullable = false)
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id", nullable = false)
    private Category category;

    private Integer maxTableCount;

    @Embedded
    private Location location;

    @Embedded
    private OperatingHours operatingHours;

    private Boolean isOpened;

    private Double averageRating;


    public static Restaurant of(String name, Long ownerId, String phone, Category category,
        int maxTableCount,
        Location location, OperatingHours operatingHours) {

        Restaurant restaurant = new Restaurant();
        restaurant.name = name;
        restaurant.ownerId = ownerId;
        restaurant.phone = phone;
        restaurant.maxTableCount = maxTableCount;
        restaurant.location = location;
        restaurant.operatingHours = operatingHours;
        restaurant.category = category;
        restaurant.isOpened = false;
        restaurant.averageRating = 0.0;

        return restaurant;
    }

    public void update(String phone, LocalTime operatingHours , LocalTime closingHours) {
        if(StringUtils.isNotBlank(phone)) this.phone = phone;
        this.operatingHours.update(operatingHours, closingHours);
    }

    public void close() {
        if (!this.isOpened) {
            throw RestaurantBusinessException.from(RestaurantErrorCode.RESTAURANT_ALREADY_CLOSED);
        }
        this.isOpened = false;
    }

}
