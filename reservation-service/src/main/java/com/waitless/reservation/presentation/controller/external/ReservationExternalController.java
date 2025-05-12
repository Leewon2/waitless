package com.waitless.reservation.presentation.controller.external;

import com.waitless.common.exception.response.MultiResponse;
import com.waitless.common.exception.response.SingleResponse;
import com.waitless.reservation.application.dto.ReservationCreateResponse;
import com.waitless.reservation.application.dto.ReservationCurrentResponse;
import com.waitless.reservation.application.dto.ReservationSearchResponse;
import com.waitless.reservation.application.scheduler.restarant.TicketingMenuFetchScheduler;
import com.waitless.reservation.application.service.command.ReservationCommandService;
import com.waitless.reservation.application.service.query.ReservationQueryService;
import com.waitless.reservation.presentation.dto.ReservationCreateRequest;
import com.waitless.reservation.presentation.dto.ReservationFindResponse;
import com.waitless.reservation.presentation.dto.ReservationSearchRequest;
import com.waitless.reservation.presentation.mapper.ReservationCommandMapper;
import com.waitless.reservation.presentation.mapper.ReservationQueryMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationExternalController {

    private final ReservationCommandService commandService;
    private final ReservationCommandMapper commandMapper;
    private final ReservationQueryMapper queryMapper;
    private final ReservationQueryService queryService;
    private final TicketingMenuFetchScheduler ticketingMenuFetchScheduler;
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

    @GetMapping
    public ResponseEntity search(@ModelAttribute ReservationSearchRequest request) {
        Page<ReservationSearchResponse> result = queryService.search(queryMapper.toReservationSearchQuery(request));
        return ResponseEntity.ok(MultiResponse.success(result));
    }

    @PatchMapping("/{reservationId}/cancel")
    public ResponseEntity cancel(@PathVariable UUID reservationId){
        commandService.cancelReservation(reservationId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{reservationId}/visit")
    public ResponseEntity visit(@PathVariable UUID reservationId){
        commandService.visitReservation(reservationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{reservationId}/queue-position")
    public ResponseEntity current(@PathVariable("reservationId") UUID reservationId) {
        ReservationCurrentResponse response = queryService.currentNumber(reservationId);
        return ResponseEntity.ok(SingleResponse.success(response));
    }

    @GetMapping("/{reservationId}/force-schedule")
    public ResponseEntity schedule(@PathVariable("reservationId") UUID reservationId) {
        ticketingMenuFetchScheduler.scheduleTest(reservationId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{reservationId}/delay")
    public ResponseEntity delay(@PathVariable UUID reservationId, @RequestParam Long count) {
        commandService.delayReservation(reservationId, count);
        return ResponseEntity.ok().build();
    }
}
