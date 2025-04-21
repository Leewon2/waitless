package com.waitless.restaurant.menu.infrastructure.adaptor.in.messaging;

import com.waitless.common.event.StockDecreasedEvent;
import com.waitless.restaurant.menu.application.service.MenuService;
import com.waitless.restaurant.menu.infrastructure.adaptor.out.messaging.StockDecreaseEventPublisher;
import com.waitless.restaurant.restaurant.application.exception.RestaurantBusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockDecreaseEventHandler {

    private final MenuService menuService;
    private final StockDecreaseEventPublisher stockDecreaseEventPublisher;

    private static final String TOPIC_NAME = "restaurant-stock-decrease";
    private static final String GROUP_ID = "restaurant-service";

    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID)
    public void handleStockDecreaseEvent(StockDecreasedEvent event) {
        log.info("[Kafka] handleStockDecreaseEvent 수신 {}", event);

        try {
            menuService.decreaseMenuAmount(event.getStockDtoList());
        } catch (RestaurantBusinessException e) {
            log.info("[kafka] 이벤트 발행 실패 {}", e.getMessage());
            stockDecreaseEventPublisher.publishStockDecreaseFailed(event);
        }
    }

}
