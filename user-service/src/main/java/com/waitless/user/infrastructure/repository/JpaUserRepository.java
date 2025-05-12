package com.waitless.user.infrastructure.repository;

import java.util.Optional;
import java.util.OptionalInt;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waitless.user.domain.entity.User;

public interface JpaUserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
