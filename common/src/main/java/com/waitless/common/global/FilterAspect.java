package com.waitless.common.global;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class FilterAspect {
    private final FilterManager filterManager;

    @Before("execution(* com.waitless..service..*(..))")
    public void enableDeletedFilter() {
        filterManager.enableFilter("deletedFilter", "isDeleted", false);
    }
}
