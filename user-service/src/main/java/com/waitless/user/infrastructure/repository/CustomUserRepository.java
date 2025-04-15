package com.waitless.user.infrastructure.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.waitless.user.domain.entity.User;

public interface CustomUserRepository{

	Page<User> findAndSearchUsers(String name, Sort.Direction sortDirection, String sortBy, Pageable pageable);

}
