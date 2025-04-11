package com.waitless.user.domain.repository;

import java.util.Optional;

import com.waitless.user.domain.entity.User;

public interface UserRepository {
	User save(User user);

	Optional<User> findByEmail(String email);

	Optional<User> findById(Long id);
}
