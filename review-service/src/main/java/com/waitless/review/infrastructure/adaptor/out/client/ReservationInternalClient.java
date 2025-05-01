package com.waitless.review.infrastructure.adaptor.out.client;

import com.waitless.common.config.FeignClientConfig;
import com.waitless.review.infrastructure.adaptor.out.client.dto.ReservationServiceRequest;
import com.waitless.review.infrastructure.adaptor.out.client.dto.ReservationServiceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "reservation-service",
        url = "http://localhost:19094",
        configuration = FeignClientConfig.class
)
public interface ReservationInternalClient {
    @PostMapping("/api/v1/app/reservations/visited")
    List<ReservationServiceResponse> getVisitedReservations(@RequestBody ReservationServiceRequest request);
}
