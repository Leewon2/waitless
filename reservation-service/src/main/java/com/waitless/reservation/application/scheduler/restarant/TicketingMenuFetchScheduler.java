package com.waitless.reservation.application.scheduler.restarant;

import com.waitless.common.dto.RestaurantStockResponseDto;
import com.waitless.reservation.application.service.redis.RedisStockService;
import com.waitless.reservation.domain.entity.TicketRestaurant;
import com.waitless.reservation.domain.repository.TicketRepository;
import com.waitless.reservation.infrastructure.adaptor.client.RestaurantClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TicketingMenuFetchScheduler {

    private final RestaurantClient restaurantClient;
    private final RedisStockService redisStockService;
    private final TicketRepository ticketRepository;

    @Scheduled(cron = "0 50 * * * *")
    public void fetchMenuStocks() {
        LocalTime openTime = LocalTime.now()
                .plusHours(1)
                .truncatedTo(ChronoUnit.HOURS);

        // 1. ticketing 식당 조회
        List<TicketRestaurant> ticketRestaurants = ticketRepository.findByOpenTime(openTime);
        if (ticketRestaurants.isEmpty()) {
            return;
        }

        List<RestaurantStockResponseDto> restaurantStock = restaurantClient.getRestaurantStock(ticketRestaurants.stream().map(TicketRestaurant::getRestaurantId).collect(Collectors.toList()));
        redisStockService.saveStock(restaurantStock);
    }
}