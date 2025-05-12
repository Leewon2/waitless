package com.waitless.user.infrastructure.repository;

import static com.waitless.user.domain.entity.QUser.*;

import java.util.List;
import java.util.Optional;

import org.hibernate.query.SortDirection;
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
import com.waitless.user.application.dto.ReadUsersDto;
import com.waitless.user.domain.entity.QUser;
import com.waitless.user.domain.entity.User;
import com.waitless.user.domain.repository.UserRepository;

import jakarta.persistence.EntityManager;

@Repository
public class UserRepositoryImpl implements UserRepository, CustomUserRepository {

	private final JpaUserRepository jpaUserRepository;
	private final JPAQueryFactory queryFactory;

	public UserRepositoryImpl(JpaUserRepository jpaUserRepository, EntityManager entityManager) {
		this.jpaUserRepository = jpaUserRepository;
		this.queryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public User save(User user) {
		return jpaUserRepository.save(user);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return jpaUserRepository.findByEmail(email);
	}

	@Override
	public Optional<User> findById(Long id) {
		return jpaUserRepository.findById(id);
	}

	@Override
	public Page<User> findAndSearchUsers(String name, Sort.Direction sortDirection, String sortBy, Pageable pageable) {
		OrderSpecifier<?> orderSpecifier = getOrderSpecifier(sortDirection, sortBy);

		BooleanBuilder builder = new BooleanBuilder();
		if (name != null && !name.isBlank()) {
			builder.and(user.name.containsIgnoreCase(name));
		}

		List<User> users = queryFactory
			.selectFrom(user)
			.where(builder)
			.orderBy(orderSpecifier)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		JPAQuery<Long> countQuery = queryFactory
			.select(user.count())
			.from(user)
			.where(builder);

		return PageableExecutionUtils.getPage(users, pageable, countQuery::fetchOne);
	}

	private OrderSpecifier<?> getOrderSpecifier(Sort.Direction direction, String sortBy) {
		Order order = direction == Sort.Direction.ASC ? Order.ASC : Order.DESC;
		switch (sortBy) {
			case "name":
				return new OrderSpecifier<>(order, user.name);
			case "createdAt":
				return new OrderSpecifier<>(order, user.createdAt);
			case "updatedAt":
			default:
				return new OrderSpecifier<>(order, user.updatedAt);
		}
	}

}
