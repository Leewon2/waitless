package com.waitless.reservation.application.interceptor;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserContext {

    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();

    private Long userId;
    private String role;

    @Builder
    public UserContext(Long userId, String role, String slackId) {
        this.userId = userId;
        this.role = role;
    }

    public UserContext() {
    }

    public static void setUserContext(UserContext context) {
        userContext.set(context);
    }
    public static UserContext getUserContext() {
        return userContext.get();
    }
    public static void clear() { userContext.remove(); }
}