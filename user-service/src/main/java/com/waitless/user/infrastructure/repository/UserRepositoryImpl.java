package com.waitless.user.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.waitless.user.domain.entity.User;
import com.waitless.user.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final JpaUserRepository jpaUserRepository;

	@Override
	public User save(User user) {
		return jpaUserRepository.save(user);
	}
}
