package com.waitless.reservation.domain.entity;

import com.waitless.common.domain.BaseTimeEntity;
import com.waitless.common.exception.BusinessException;
import com.waitless.reservation.exception.exception.ReservationErrorCode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "p_reservation")
@Where(clause = "is_deleted = false")
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Reservation extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "reservation_id")
    private UUID id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private UUID restaurantId;

    @Column(nullable = false)
    private String restaurantName;

    @Column(nullable = false)
    private Integer peopleCount;

    @Column(nullable = false)
    private Long reservationNumber;

    @Column(nullable = false)
    private Integer delayCount = 3; //순서 미루기 3번 가능. 미룰 때마다 delayCount--

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    @Column(nullable = false)
    private LocalDate reservationDate;

    @OneToMany(mappedBy = "reservation", orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<ReservationMenu> menus = new ArrayList<>();

    /* 추후 의논해야 할 사항
    private UUID couponId;  // 사용된 쿠폰 ID

    @Column(nullable = false)
    private int usedPoint = 0;  // 사용한 포인트

    @Column(nullable = false)
    private int totalAmount;   // 총 결제 금액 (포인트/쿠폰 적용 후 실제 결제금액)

    private int originalAmount; // 쿠폰/포인트 적용 전 금액
    */

    public static Reservation create(UUID storeId, String restaurantName,
                                     Integer peopleCount, Long reservationNumber,
                                     LocalDate reservationDate, ReservationStatus status,
                                     List<ReservationMenu> menus, Long userId) {

        Reservation reservation = new Reservation();
        reservation.restaurantId = storeId;
        reservation.restaurantName = restaurantName;
        reservation.peopleCount = peopleCount;
        reservation.reservationNumber = reservationNumber;
        reservation.reservationDate = reservationDate;
        reservation.status = status;
        reservation.userId = userId;

        for (ReservationMenu menu : menus) {
            reservation.addMenu(menu);
        }

        return reservation;
    }

    private void addMenu(ReservationMenu menu) {
        this.menus.add(menu);
        menu.addReservation(this);
    }

    public Integer getTotalPrice() {
        return menus.stream()
                .mapToInt(m -> m.getPrice() * m.getQuantity())
                .sum();
    }

    public void cancel() {
        validateWaitingStatus();
        this.status = ReservationStatus.CANCELLED;
    }

    public void visit() {
        validateWaitingStatus();
        this.status = ReservationStatus.COMPLETED;
    }

    public void validateWaitingStatus() {
        if (this.status != ReservationStatus.WAITING) {
            throw BusinessException.from(ReservationErrorCode.RESERVATION_STATUS_ERROR);
        }
    }

    public void minusDelayCount() {
        this.delayCount--;
    }
}
