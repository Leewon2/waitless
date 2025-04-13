package com.waitless.reservation.presentation.dto;

import java.io.Serializable;

public record ReservationMenuResponse(Integer quantity, String menuName) implements Serializable {
}
