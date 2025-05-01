package com.waitless.reservation.application.event;

import com.waitless.common.event.StockDecreasedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
@Slf4j
public class StockEventHandler {

    private final StockDecreaseEventProducer stockDecreaseEventProducer;

    @Async("messageExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReservationVisited(StockDecreasedEvent event) {
        try {
            stockDecreaseEventProducer.publish(event);
        } catch (Exception e) {
            log.error("Error sending stock request", e);
        }
    }
}