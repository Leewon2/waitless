package com.waitless.restaurant.menu.infrastructure.adaptor.in.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.waitless.common.dto.StockDto;
import com.waitless.restaurant.menu.application.service.MenuService;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockDecreaseEventHandler {

    private final ObjectMapper objectMapper;
    private final MenuService menuService;

    private static final String TOPIC_NAME = "restaurant.stock.decrease";
    private static final String GROUP_ID = "restaurant-service";

    @KafkaListener(topics = TOPIC_NAME, groupId = GROUP_ID)
    public void handleStockDecreaseEvent(String message) throws JsonProcessingException {
        log.info("[Kafka] handleStockDecreaseEvent 수신 {}" , message);

        //json -> List<StockDto> 로 변환
        List<StockDto> stockList = objectMapper.readValue(message,
            new TypeReference<List<StockDto>>(){});

        menuService.decreaseMenuAmount(stockList);
    }

}
