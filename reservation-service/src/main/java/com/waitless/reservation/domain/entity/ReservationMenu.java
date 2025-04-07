package com.waitless.reservation.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Where(clause = "is_deleted = false")
@Table(name = "p_reservation_menu")
public class ReservationMenu {
    @Id
    @GeneratedValue
    @Column(name = "reservation_menu_id")
    private UUID id;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private UUID menuId;

    @Column(nullable = false)
    private String menuName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;
}
