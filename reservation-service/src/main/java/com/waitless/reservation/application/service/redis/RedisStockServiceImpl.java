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
        validateMenuKeysExist(restaurantId, menus);

        List<String> keys = new ArrayList<>();
        List<String> args = new ArrayList<>();

        for (MenuCommandDto menu : menus) {
            keys.add("stock:" + restaurantId + ":" + menu.menuId());
            args.add(menu.quantity().toString());
            args.add(menu.menuId().toString());
        }

        String luaScript = redisLuaScriptService.loadScript(DECREASE_STOCK_SCRIPT);

        DefaultRedisScript<List> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(luaScript);
        redisScript.setResultType(List.class);

        @SuppressWarnings("unchecked")
        List<String> result = redisTemplate.execute(redisScript, keys, args.toArray());

        if (result == null || result.isEmpty()) {
            throw BusinessException.from(ReservationErrorCode.UNKNOWN_LUA_RESULT, result);
        }

        if (LuaResultType.from(result.get(0))
                .map(type -> type == LuaResultType.SUCCESS)
                .orElse(false)) {
            return createReservationNumber(restaurantId);
        }

        // 실패 메뉴 ID 리스트
        List<UUID> insufficient = result.stream().map(UUID::fromString).toList();
        throw BusinessException.from(ReservationErrorCode.RESERVATION_STOCK_ERROR, insufficient);
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

    /**
     * 존재하는 메뉴인지 체크
     */
    private void validateMenuKeysExist(UUID restaurantId, List<MenuCommandDto> menus) {
        List<String> missing = new ArrayList<>();

        for (MenuCommandDto menu : menus) {
            String key = "stock:" + restaurantId + ":" + menu.menuId();
            if (Boolean.FALSE.equals(redisTemplate.hasKey(key))) {
                missing.add(menu.menuId().toString());
            }
        }

        if (!missing.isEmpty()) {
            throw BusinessException.from(ReservationErrorCode.RESERVATION_MENU_NOTFOUND_ERROR, missing);
        }
    }
}