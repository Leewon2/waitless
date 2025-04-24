package com.waitless.benefit.coupon.infrastructure.repository;

import static com.waitless.benefit.coupon.domain.entity.QCouponHistory.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.waitless.benefit.coupon.domain.entity.CouponHistory;
import com.waitless.benefit.coupon.domain.repository.CouponHistoryRepository;
import com.waitless.benefit.coupon.domain.repository.CustomCouponHistoryRepository;
import com.waitless.common.domain.Role;

import jakarta.persistence.EntityManager;

@Repository
public class CouponHistoryRepositoryImpl implements CouponHistoryRepository, CustomCouponHistoryRepository {

	private final JpaCouponHistoryRepository jpaCouponHistoryRepository;
	private final JPAQueryFactory queryFactory;

	public CouponHistoryRepositoryImpl(JpaCouponHistoryRepository jpaCouponHistoryRepository, EntityManager entityManager) {
		this.jpaCouponHistoryRepository = jpaCouponHistoryRepository;
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public CouponHistory save(CouponHistory couponHistory) {
		return jpaCouponHistoryRepository.save(couponHistory);
	}

	@Override
	public Optional<CouponHistory> findById(UUID id) {
		return jpaCouponHistoryRepository.findById(id);
	}

	@Override
	public Page<CouponHistory> findAndSearchCouponHistories(
		String title, Sort.Direction sortDirection, String sortBy, Long userId, Role role, Pageable pageable) {
		OrderSpecifier<?> orderSpecifier = getOrderSpecifier(sortDirection, sortBy);
		BooleanBuilder builder = new BooleanBuilder();
		if (role != Role.ADMIN) {
			builder.and(couponHistory.userId.eq(userId));
		}
		if (title != null && !title.isBlank()) {
			builder.and(couponHistory.title.containsIgnoreCase(title));
		}

		List<CouponHistory> couponHistories = queryFactory
			.selectFrom(couponHistory)
			.where(builder)
			.orderBy(orderSpecifier)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(couponHistory.count())
			.from(couponHistory)
			.where(builder);

		return PageableExecutionUtils.getPage(couponHistories, pageable, countQuery::fetchOne);
	}

	@Override
	public List<CouponHistory> findInvalidIssuedCoupons() {
		BooleanBuilder builder = new BooleanBuilder();
		builder.or(couponHistory.isValid.eq(false));
		builder.or(couponHistory.expiredAt.before(LocalDateTime.now()));
		List<CouponHistory> couponHistories = queryFactory
			.selectFrom(couponHistory)
			.where(builder)
			.fetch();
		return couponHistories;
	}

	private OrderSpecifier<?> getOrderSpecifier(Sort.Direction direction, String sortBy) {
		Order order = direction == Sort.Direction.ASC ? Order.ASC : Order.DESC;
		switch (sortBy) {
			case "title":
				return new OrderSpecifier<>(order, couponHistory.title);
			case "createdAt":
				return new OrderSpecifier<>(order, couponHistory.createdAt);
			case "updatedAt":
			default:
				return new OrderSpecifier<>(order, couponHistory.updatedAt);
		}
	}
}
