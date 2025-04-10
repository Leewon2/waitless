package com.waitless.reservation.presentation.controller.external;

import com.waitless.common.exception.response.SingleResponse;
import com.waitless.reservation.application.service.command.ReservationCommandService;
import com.waitless.reservation.presentation.dto.ReservationCreateRequest;
import com.waitless.reservation.presentation.dto.ReservationCreateResponse;
import com.waitless.reservation.presentation.mapper.ReservationCommandMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationExternalController {

    private final ReservationCommandService commandService;
    private final ReservationCommandMapper commandMapper;

    @PostMapping
    public ResponseEntity createReservation(@RequestBody @Valid ReservationCreateRequest reservationCreateRequest) {
        ReservationCreateResponse response = commandService.createReservation(commandMapper.toCommand(reservationCreateRequest));
        return ResponseEntity.ok(SingleResponse.success(response));
    }
}
