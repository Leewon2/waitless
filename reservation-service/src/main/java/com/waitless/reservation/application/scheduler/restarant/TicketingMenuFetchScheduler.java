package com.waitless.reservation.application.scheduler.restarant;

import com.waitless.common.dto.RestaurantStockResponseDto;
import com.waitless.common.exception.BusinessException;
import com.waitless.reservation.application.service.redis.RedisStockService;
import com.waitless.reservation.domain.entity.TicketRestaurant;
import com.waitless.reservation.domain.repository.TicketRepository;
import com.waitless.reservation.exception.exception.ReservationErrorCode;
import com.waitless.reservation.infrastructure.adaptor.client.RestaurantClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
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

    /**
     * 원래는 매 시 50분마다 스케쥴러가 작동해서 ex)오전 11시 50분에 오픈이 12시인 식당에 메뉴재고를 가져와서 레디스에 넣지만
     * 테스트를 위해 스케쥴러 강제 호출해서 식당id를 받아서 그 식당에 메뉴 재고를 받아와서 레디스에 넣음
     */
    public void scheduleTest(UUID restaurantId) {
        // 1. ticketing 식당 조회
        List<TicketRestaurant> ticketRestaurants = ticketRepository.findByRestaurantId(restaurantId);
        if (ticketRestaurants.isEmpty()) {
            throw BusinessException.from(ReservationErrorCode.RESERVATION_TICKETING_RESTAURANT_NOT_FOUND);
        }

        List<RestaurantStockResponseDto> restaurantStock = restaurantClient.getRestaurantStock(ticketRestaurants.stream().map(TicketRestaurant::getRestaurantId).collect(Collectors.toList()));
        redisStockService.saveStock(restaurantStock);
    }
}