package com.waitless.reservation.presentation.controller.external;

import com.waitless.common.exception.response.SingleResponse;
import com.waitless.reservation.application.service.command.ReservationCommandService;
import com.waitless.reservation.application.service.query.ReservationQueryService;
import com.waitless.reservation.presentation.dto.ReservationCreateRequest;
import com.waitless.reservation.presentation.dto.ReservationCreateResponse;
import com.waitless.reservation.presentation.dto.ReservationFindResponse;
import com.waitless.reservation.presentation.mapper.ReservationCommandMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservation")
public class ReservationExternalController {

    private final ReservationCommandService commandService;
    private final ReservationCommandMapper commandMapper;
    private final ReservationQueryService queryService;

    @PostMapping
    public ResponseEntity createReservation(@RequestBody @Valid ReservationCreateRequest reservationCreateRequest) {
        ReservationCreateResponse response = commandService.createReservation(commandMapper.toCommand(reservationCreateRequest));
        return ResponseEntity.ok(SingleResponse.success(response));
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity ReservationFindOne(@PathVariable UUID reservationId) {
        ReservationFindResponse response = queryService.findOne(reservationId);
        return ResponseEntity.ok(SingleResponse.success(response));
    }
}
