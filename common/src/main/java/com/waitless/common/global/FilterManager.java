package com.waitless.common.global;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class FilterManager {
    private final EntityManager entityManager;

    public void enableFilter(String filterName, String paramName, Object paramValue) {
        Session session = entityManager.unwrap(Session.class);
        Filter filter = session.enableFilter(filterName);
        filter.setParameter(paramName, paramValue);
    }

    public void disableFilter(String filterName) {
        Session session = entityManager.unwrap(Session.class);
        session.disableFilter(filterName);
    }

    public <T> T withDisabledFilter(String filterName, Supplier<T> action) {
        disableFilter(filterName);
        try {
            return action.get();
        } finally {
            enableFilter(filterName, "isDeleted", false);
        }
    }
}

