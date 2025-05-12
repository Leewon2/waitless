package com.waitless.reservation.application.service.redis;

import com.waitless.common.dto.RestaurantStockResponseDto;
import com.waitless.common.dto.StockDto;
import com.waitless.common.exception.BusinessException;
import com.waitless.reservation.application.dto.CancelMenuDto;
import com.waitless.reservation.application.dto.LuaResultType;
import com.waitless.reservation.exception.exception.ReservationErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.waitless.reservation.application.dto.ReservationCreateCommand.MenuCommandDto;

@Service
@RequiredArgsConstructor
public class RedisStockServiceImpl implements RedisStockService {

    private final StringRedisTemplate redisTemplate;
    private final RedisLuaScriptService redisLuaScriptService;

    private static final String RESERVATION_NUMBER_PREFIX = "reservation:number:";
    private static final String RESERVATION_TEAM_COUNT_PREFIX = "reservation:teamCount:";
    private static final String RESERVATION_TEAM_LIMIT_PREFIX = "reservation:teamLimit:";
    private static final String DECREASE_STOCK_SCRIPT = "classpath:lua/stock_decrease.lua";
    private static final String INCREMENT_STOCK_SCRIPT = "classpath:lua/stock_increment.lua";

    @Override
    public Long decrementStock(UUID restaurantId, List<MenuCommandDto> menus) {
        validateRestaurantExists(restaurantId);
        validateMenuKeysExist(restaurantId, menus);

        List<String> keys = new ArrayList<>();
        keys.add(RESERVATION_TEAM_COUNT_PREFIX + restaurantId);
        keys.add(RESERVATION_NUMBER_PREFIX + restaurantId); // 대기표 순번을 위한 키

        String teamLimit = redisTemplate.opsForValue().get(RESERVATION_TEAM_LIMIT_PREFIX + restaurantId);
        if (teamLimit == null) {
            throw new RuntimeException("식당 팀 최대 인원 없음");
        }

        List<String> args = new ArrayList<>();
        args.add(teamLimit);
        args.add(String.valueOf(menus.size()));

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

        String resultTypeString = result.get(0);
        LuaResultType type = LuaResultType.from(resultTypeString)
                .orElseThrow(() -> BusinessException.from(ReservationErrorCode.UNKNOWN_LUA_RESULT, result));

        if (type == LuaResultType.SUCCESS) {
            return Long.parseLong(result.get(1));
        }

        switch (type) {
            case TEAM_OVER -> throw BusinessException.from(ReservationErrorCode.RESERVATION_TEAM_LIMIT_EXCEEDED);
            case INSUFFICIENT -> {
                List<UUID> insufficient = result.stream().skip(1).map(UUID::fromString).toList();
                throw BusinessException.from(ReservationErrorCode.RESERVATION_STOCK_ERROR, insufficient);
            }
            default -> throw new RuntimeException("Lua Script ERROR");
        }
    }

    @Override
    public void incrementStock(UUID restaurantId, List<CancelMenuDto> menus) {
        List<String> keys = new ArrayList<>();
        keys.add(RESERVATION_TEAM_COUNT_PREFIX + restaurantId);

        List<String> args = new ArrayList<>();
        args.add(String.valueOf(menus.size()));

        for (CancelMenuDto menu : menus) {
            keys.add("stock:" + restaurantId + ":" + menu.menuId());
            args.add(menu.quantity().toString());
            args.add(menu.menuId().toString());
        }

        String luaScript = redisLuaScriptService.loadScript(INCREMENT_STOCK_SCRIPT);

        DefaultRedisScript<List> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(luaScript);
        redisScript.setResultType(List.class);

        @SuppressWarnings("unchecked")
        List<String> result = redisTemplate.execute(redisScript, keys, args.toArray());

        if (result == null || result.isEmpty()) {
            throw BusinessException.from(ReservationErrorCode.UNKNOWN_LUA_RESULT, result);
        }

        String resultType = result.get(0);
        if ("SUCCESS".equals(resultType)) return;

        if ("MISSING_STOCK".equals(resultType)) {
            UUID menuId = UUID.fromString(result.get(1));
            throw BusinessException.from(ReservationErrorCode.RESERVATION_STOCK_RESTORE_ERROR, menuId);
        }

        throw new RuntimeException("Lua Script ERROR");
    }

    @Override
    public void saveStock(List<RestaurantStockResponseDto> restaurantStockList) {
        for (RestaurantStockResponseDto restaurant : restaurantStockList) {
            UUID restaurantId = restaurant.restaurantId();

            redisTemplate.opsForValue().set(RESERVATION_TEAM_LIMIT_PREFIX + restaurantId, String.valueOf(restaurant.maxTableCount()));
            redisTemplate.opsForValue().set(RESERVATION_TEAM_COUNT_PREFIX + restaurantId, "0");

            redisTemplate.opsForValue().set(RESERVATION_NUMBER_PREFIX + restaurantId, "0");

            for (StockDto stock : restaurant.stockList()) {
                String stockKey = "stock:" + restaurantId + ":" + stock.menuId();
                redisTemplate.opsForValue().set(stockKey, String.valueOf(stock.amount()));
            }
        }
    }

    @Override
    public void closedRestaurant(UUID restaurantId) {
        redisTemplate.delete(RESERVATION_NUMBER_PREFIX + restaurantId);
        redisTemplate.delete(RESERVATION_TEAM_COUNT_PREFIX + restaurantId);
        redisTemplate.delete(RESERVATION_TEAM_LIMIT_PREFIX + restaurantId);

        Set<String> stockKeys = redisTemplate.keys("stock:" + restaurantId + ":*");
        if (stockKeys != null && !stockKeys.isEmpty()) {
            redisTemplate.delete(stockKeys);
        }

        Set<String> queueKeys = redisTemplate.keys("reservation:queue:" + restaurantId);
        if (queueKeys != null && !queueKeys.isEmpty()) {
            redisTemplate.delete(queueKeys);
        }
    }

    /**
     * 존재하는 식당인지 체크
     */
    private void validateRestaurantExists(UUID restaurantId) {
        Boolean exists = redisTemplate.hasKey(RESERVATION_NUMBER_PREFIX + restaurantId);
        if (Boolean.FALSE.equals(exists)) {
            throw BusinessException.from(ReservationErrorCode.RESERVATION_RESTAURANT_NOT_FOUND);
        }
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