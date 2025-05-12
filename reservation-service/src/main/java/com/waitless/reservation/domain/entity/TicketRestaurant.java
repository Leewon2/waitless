package com.waitless.reservation.domain.entity;

import com.waitless.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "p_ticket_restaurant")
public class TicketRestaurant extends BaseTimeEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID restaurantId;

    @Column(nullable = false)
    private LocalTime openTime; // 오픈 시간 (ex: 10:00)

    public static TicketRestaurant create(UUID restaurantId, LocalTime openTime) {
        TicketRestaurant ticketRestaurant = new TicketRestaurant();
        ticketRestaurant.restaurantId = restaurantId;
        ticketRestaurant.openTime = openTime;
        return ticketRestaurant;
    }
}
