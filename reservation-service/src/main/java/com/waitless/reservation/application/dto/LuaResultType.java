package com.waitless.reservation.application.dto;

import java.util.Optional;

public enum LuaResultType {
    SUCCESS,
    MISSING,
    INSUFFICIENT,
    TEAM_OVER;

    public static Optional<LuaResultType> from(String value) {
        for (LuaResultType type : values()) {
            if (value.equalsIgnoreCase(type.name())) {
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }
}