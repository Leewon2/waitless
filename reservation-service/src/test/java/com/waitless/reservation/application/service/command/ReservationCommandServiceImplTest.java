package com.waitless.reservation.application.service.command;

import com.waitless.common.exception.BusinessException;
import com.waitless.reservation.application.dto.ReservationCreateCommand;
import com.waitless.reservation.application.service.redis.RedisStockService;
import com.waitless.reservation.domain.entity.Reservation;
import com.waitless.reservation.domain.repository.ReservationRepository;
import com.waitless.reservation.exception.exception.ReservationErrorCode;
import com.waitless.reservation.application.dto.ReservationCreateResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class ReservationCommandServiceImplTest {

    @Autowired
    ReservationCommandService reservationCommandService;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    RedisStockService redisStockService;

    private static final String RESERVATION_NUMBER_PREFIX = "reservation:number:";

    private static final String RESTAURANT_ID = "d1f44111-b72c-4e0a-8c70-123456789abc";
    private static final String ERROR_RESTAURANT_ID = "ddf44111-b72c-4e0a-8c70-123456789abc";

    private static final String MENU_ID_1 = "a1111111-bbbb-4ccc-dddd-123456789abc";
    private static final String MENU_ID_2 = "b2222222-eeee-4fff-aaaa-987654321def";

    private static final String ERROR_MENU_ID_1 = "b2222222-eeee-4fff-aaaa-987654321de1";
    private static final String ERROR_MENU_ID_2 = "b2222222-eeee-4fff-aaaa-987654321de2";

    private static final String STOCK_KEY_1 = "stock:" + RESTAURANT_ID + ":" + MENU_ID_1;
    private static final String STOCK_KEY_2 = "stock:" + RESTAURANT_ID + ":" + MENU_ID_2;
    private static final String RESERVATION_NUMBER_KEY = "reservation:number:" + RESTAURANT_ID;

    @BeforeEach
    void setUp() {
        redisTemplate.opsForValue().set(RESERVATION_NUMBER_KEY, "0");
        redisTemplate.opsForValue().set(STOCK_KEY_1, "10000");
        redisTemplate.opsForValue().set(STOCK_KEY_2, "10000");
        redisTemplate.opsForValue().set("reservation:teamLimit:" + RESTAURANT_ID, "10000");
        redisTemplate.opsForValue().set("reservation:teamCount:" + RESTAURANT_ID, "0");
    }

    @AfterEach
    void tearDown() {
        redisTemplate.delete(RESERVATION_NUMBER_KEY);
        redisTemplate.delete(STOCK_KEY_1);
        redisTemplate.delete(STOCK_KEY_2);
        redisTemplate.delete("reservation:teamLimit:" + RESTAURANT_ID);
        redisTemplate.delete("reservation:teamCount:" + RESTAURANT_ID);
    }

    @Test
    @DisplayName("예약 생성 테스트")
    void ReservationCreateTest() {
        // given
        UUID restaurantId = UUID.fromString(RESTAURANT_ID);
        Long userId = 1L;
        LocalDate reservationDate = LocalDate.of(2025, 4, 10);

        List<ReservationCreateCommand.MenuCommandDto> menus = List.of(
                new ReservationCreateCommand.MenuCommandDto(
                        UUID.fromString(MENU_ID_2),
                        "치츠돈까스",
                        1,
                        8000
                ),
                new ReservationCreateCommand.MenuCommandDto(
                        UUID.fromString(MENU_ID_1),
                        "치츠돈까스",
                        1,
                        15000
                )
        );

        ReservationCreateCommand command = new ReservationCreateCommand(
                restaurantId,
                menus,
                "연돈",
                4,
                reservationDate,
                userId
        );

        // when
        ReservationCreateResponse response = reservationCommandService.createReservation(command);
        Reservation findReservation = reservationRepository.findById(response.reservationId()).orElseThrow(RuntimeException::new);

        // then
        assertThat(findReservation.getId()).isEqualTo(response.reservationId());
        assertThat(findReservation.getPeopleCount()).isEqualTo(response.peopleCount());
        assertThat(findReservation.getReservationNumber()).isEqualTo(response.reservationNumber());
        assertThat(findReservation.getReservationDate()).isEqualTo(response.reservationDate());
        assertThat(findReservation.getUserId()).isEqualTo(userId);

        // 예약 순번 Redis 검증
        assertThat(redisTemplate.opsForValue().get(RESERVATION_NUMBER_KEY)).isEqualTo("1");

        // 재고 감소 Redis 검증
        assertThat(redisTemplate.opsForValue().get(STOCK_KEY_2)).isEqualTo("9999");
    }

    @Test
    @DisplayName("Redis 동시성 테스트 :: 재고 차감, 대기번호, 최대 인원")
    void redisConcurrencyTest() throws InterruptedException {
        //given
        int threadCount = 10000;
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(threadCount);

        String stockKey = "stock:" + RESTAURANT_ID + ":" + MENU_ID_1;
        String numberKey = "reservation:number:" + RESTAURANT_ID;
        String limit = "reservation:teamCount:" + RESTAURANT_ID;

        //when
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    ReservationCreateCommand command = new ReservationCreateCommand(
                            UUID.fromString(RESTAURANT_ID),
                            List.of(new ReservationCreateCommand.MenuCommandDto(UUID.fromString(MENU_ID_1), "돈까스", 1, 10000)),
                            "연돈",
                            2,
                            LocalDate.of(2025, 4, 10),
                            1L
                    );
                    reservationCommandService.createReservation(command);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // then
        String finalStock = redisTemplate.opsForValue().get(stockKey);
        String finalNumber = redisTemplate.opsForValue().get(numberKey);
        String finalLimitNumber = redisTemplate.opsForValue().get(limit);

        assertThat(finalStock).isEqualTo("0");
        assertThat(finalNumber).isEqualTo("10000");
        assertThat(finalLimitNumber).isEqualTo("10000");
    }

    @Test
    @DisplayName("존재하지 않는 식당 예외 처리 테스트")
    void validateRestaurant() {
        // when
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> redisStockService.decrementStock(
                        UUID.fromString(ERROR_RESTAURANT_ID),
                        List.of(
                                new ReservationCreateCommand.MenuCommandDto(
                                        UUID.fromString(MENU_ID_1), "치츠돈까스", 1, 8000
                                ),
                                new ReservationCreateCommand.MenuCommandDto(
                                        UUID.fromString(MENU_ID_2), "치츠돈까스", 1, 15000
                                )
                        )
                )
        );

        // then
        assertThat(exception.getErrorCode()).isEqualTo(ReservationErrorCode.RESERVATION_RESTAURANT_NOT_FOUND);
    }

    @Test
    @DisplayName("존재하지 않는 메뉴 예외 처리 테스트")
    void validateMenu() {
        // when
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> redisStockService.decrementStock(
                        UUID.fromString(RESTAURANT_ID),
                        List.of(
                                new ReservationCreateCommand.MenuCommandDto(
                                        UUID.fromString(ERROR_MENU_ID_1), "치츠돈까스", 1, 8000
                                ),
                                new ReservationCreateCommand.MenuCommandDto(
                                        UUID.fromString(ERROR_MENU_ID_2), "치츠돈까스", 1, 15000
                                )
                        )
                )
        );

        // then

        //예외 터지는 지
        assertThat(exception.getErrorCode()).isEqualTo(ReservationErrorCode.RESERVATION_MENU_NOTFOUND_ERROR);

        @SuppressWarnings("unchecked")
        List<UUID> data = (List<UUID>) exception.getData();
        assertThat(data).hasSize(2);
    }

    @Test
    @DisplayName("예약 팀 수 제한 초과 시 예외 발생 테스트")
    void reservationTeamLimitExceededTest() throws InterruptedException {
        // given
        int limit = 10000;
        int requestCount = 10001;

        redisTemplate.opsForValue().set("reservation:teamLimit:" + RESTAURANT_ID, String.valueOf(limit));
        redisTemplate.opsForValue().set("reservation:teamCount:" + RESTAURANT_ID, "0");

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        CountDownLatch latch = new CountDownLatch(requestCount);

        List<Throwable> errors = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < requestCount; i++) {
            executorService.execute(() -> {
                try {
                    ReservationCreateCommand command = new ReservationCreateCommand(
                            UUID.fromString(RESTAURANT_ID),
                            List.of(new ReservationCreateCommand.MenuCommandDto(
                                    UUID.fromString(MENU_ID_1), "돈까스", 1, 10000)),
                            "연돈",
                            2,
                            LocalDate.of(2025, 4, 10),
                            1L
                    );
                    reservationCommandService.createReservation(command);
                } catch (Throwable e) {
                    errors.add(e);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        // then
        long teamOverCount = errors.stream()
                .filter(e -> e instanceof BusinessException &&
                        ((BusinessException) e).getErrorCode() == ReservationErrorCode.RESERVATION_TEAM_LIMIT_EXCEEDED)
                .count();

        assertEquals(1, teamOverCount, "팀 수 초과 예외는 딱 1번 발생해야 함");

    }
}