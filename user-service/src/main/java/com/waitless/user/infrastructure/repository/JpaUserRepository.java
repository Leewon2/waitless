package com.waitless.user.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waitless.user.domain.entity.User;

public interface JpaUserRepository extends JpaRepository<User, Long> {
}
