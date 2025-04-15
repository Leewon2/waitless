package com.waitless.restaurant.restaurant.infrastructure.persistence;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.waitless.restaurant.restaurant.domain.entity.QRestaurant;
import com.waitless.restaurant.restaurant.domain.entity.Restaurant;
import com.waitless.restaurant.restaurant.domain.repository.RestaurantQueryRepository;
import io.micrometer.common.util.StringUtils;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RestaurantQueryRepositoryImpl implements RestaurantQueryRepository {

    private final JPAQueryFactory queryFactory;

    QRestaurant restaurant = QRestaurant.restaurant;

    public Page<Restaurant> searchRestaurant(String name, String categoryId,Pageable pageable) {

        BooleanExpression whereClause = buildWhereClause(name, categoryId);

        List<Restaurant> restaurantList = queryFactory
            .selectFrom(restaurant)
            .where(whereClause)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();


        long totalCount = Optional.ofNullable(queryFactory
            .select(restaurant.count())
            .from(restaurant)
            .where(whereClause)
            .fetchOne()
        ).orElse(0L);

        return new PageImpl<>(restaurantList, pageable, totalCount);
    }


    private BooleanExpression buildWhereClause(String name, String categoryId) {

        BooleanExpression whereClause = restaurant.isDeleted.eq(false);
        if (StringUtils.isNotEmpty(name)) whereClause = whereClause.and(restaurant.name.contains(name));
        if (StringUtils.isNotEmpty(categoryId)) whereClause = whereClause.and(restaurant.category.id.eq(Long.parseLong(categoryId)));

     return whereClause;
    }

}
