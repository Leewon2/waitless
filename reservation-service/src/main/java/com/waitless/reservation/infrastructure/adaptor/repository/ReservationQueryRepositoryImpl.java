package com.waitless.reservation.infrastructure.adaptor.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.waitless.reservation.application.dto.ReservationSearchQuery;
import com.waitless.reservation.application.interceptor.UserContext;
import com.waitless.reservation.domain.entity.QReservation;
import com.waitless.reservation.domain.entity.Reservation;
import com.waitless.reservation.domain.repository.ReservationQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReservationQueryRepositoryImpl implements ReservationQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Reservation> findByCustomCondition(ReservationSearchQuery query) {
        QReservation reservation = QReservation.reservation;
        BooleanBuilder builder = buildCondition(query);

        OrderSpecifier<?> orderSpecifier = getCreatedAtOrder(query.direction());

        int offset = (query.page() - 1) * query.size();
        int limit = query.size();

        List<Reservation> content = fetchContent(reservation, builder, orderSpecifier, offset, limit);
        long total = fetchTotal(reservation, builder);

        return new PageImpl<>(content, PageRequest.of(query.page() - 1, query.size()), total);
    }

    private BooleanBuilder buildCondition(ReservationSearchQuery query) {
        Long userId = UserContext.getUserContext().getUserId();
        QReservation reservation = QReservation.reservation;
        BooleanBuilder builder = new BooleanBuilder();

        if (query.status() != null) {
            builder.and(reservation.status.eq(query.status()));
        }
        if (userId != null) {
            builder.and(reservation.userId.eq(userId));
        }

        return builder;
    }

    private OrderSpecifier<?> getCreatedAtOrder(String direction) {
        QReservation reservation = QReservation.reservation;

        if (direction.equalsIgnoreCase("asc")) {
            return reservation.createdAt.asc();
        } else if (direction.equalsIgnoreCase("desc")) {
            return reservation.createdAt.desc();
        } else {
            throw new IllegalArgumentException("Invalid sort direction: " + direction);
        }
    }

    private List<Reservation> fetchContent(QReservation reservation, BooleanBuilder builder,
                                           OrderSpecifier<?> orderSpecifier, int offset, int limit) {
        return queryFactory
                .selectFrom(reservation)
                .where(builder)
                .orderBy(orderSpecifier)
                .offset(offset)
                .limit(limit)
                .fetch();
    }

    private long fetchTotal(QReservation reservation, BooleanBuilder builder) {
        return Optional.ofNullable(
                queryFactory
                        .select(reservation.count())
                        .from(reservation)
                        .where(builder)
                        .fetchOne()
        ).orElse(0L);
    }
}