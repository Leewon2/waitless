package com.waitless.user.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.waitless.user.domain.entity.User;

public interface UserRepository {
	User save(User user);

	Optional<User> findByEmail(String email);

	Optional<User> findById(Long id);

	Page<User> findAndSearchUsers(String name, Sort.Direction sortDirection, String sortBy, Pageable pageable);
}
