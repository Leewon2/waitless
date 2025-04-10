package com.waitless.reservation.application.service.redis;

import com.waitless.common.exception.BusinessException;
import com.waitless.reservation.application.dto.LuaResultType;
import com.waitless.reservation.exception.exception.ReservationErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.waitless.reservation.application.dto.ReservationCreateCommand.MenuCommandDto;

@Service
@RequiredArgsConstructor
public class RedisStockServiceImpl implements RedisStockService {

    private final StringRedisTemplate redisTemplate;
    private final RedisLuaScriptService redisLuaScriptService;
    private final RedisReservationService redisReservationService;

    private static final String DECREASE_STOCK_SCRIPT = "classpath:lua/stock_decrease.lua";

    @Override
    public Long decrementStock(UUID restaurantId, List<MenuCommandDto> menus) {
        validateExistRestaurant(restaurantId);
        List<String> keys = new ArrayList<>();
        List<String> args = new ArrayList<>();

        menus.forEach(menu -> {
            var menuId = menu.menuId();
            var quantity = menu.quantity();
            keys.add("stock:" + restaurantId + ":" + menuId);
            args.add(quantity.toString());
        });

        for (MenuCommandDto menu : menus) {
            args.add(menu.menuId().toString());
        }

        String luaScript = redisLuaScriptService.loadScript(DECREASE_STOCK_SCRIPT);

        DefaultRedisScript<List> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(luaScript);
        redisScript.setResultType(List.class);

        @SuppressWarnings("unchecked")
        List<String> result = redisTemplate.execute(redisScript, keys, args.toArray());

        if (result.isEmpty()) throw new RuntimeException("Redis Lua 실행 실패");

        if (LuaResultType.from(result.get(0)).orElse(null) == LuaResultType.SUCCESS) {
            return createReservationNumber(restaurantId);
        }

        //재고가 부족한 메뉴인지, 존재하지 않는 메뉴인지 구분하기 위한 로직
        List<UUID> missingMenus = new ArrayList<>();
        List<UUID> insufficientMenus = new ArrayList<>();

        for (String res : result) {
            String[] parts = res.split(":");

            LuaResultType.from(parts[0]).ifPresent(type -> {
                UUID menuId = UUID.fromString(parts[1]);

                switch (type) {
                    case MISSING -> missingMenus.add(menuId);
                    case INSUFFICIENT -> insufficientMenus.add(menuId);
                }
            });
        }

        if (!missingMenus.isEmpty()) {
            throw BusinessException.from(ReservationErrorCode.RESERVATION_MENU_NOTFOUND_ERROR, missingMenus);
        }

        if (!insufficientMenus.isEmpty()) {
            throw BusinessException.from(ReservationErrorCode.RESERVATION_STOCK_ERROR, insufficientMenus);
        }

        throw BusinessException.from(ReservationErrorCode.UNKNOWN_LUA_RESULT, result);
    }

    /**
     * 대기번호 발급
     */
    private Long createReservationNumber(UUID restaurantId) {
        return redisReservationService.createReservationNumber(restaurantId);
    }

    /**
     * 식당 존재 유무 확인
     */
    private void validateExistRestaurant(UUID restaurantId) {
        redisReservationService.validateRestaurantExists(restaurantId);
    }
}