package com.waitless.restaurant.restaurant.infrastructure.adaptor.in.client;

import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
    name = "reservation-service",
    url = "http://localhost:19094/api/reservation/app"
)
public interface ReservationInternalClient {

    @PostMapping("/{restaurantId}/closed")
    void close(@PathVariable("restaurantId") UUID restaurantId);
}
