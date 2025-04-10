package com.waitless.user.domain.repository;

import com.waitless.user.domain.entity.User;

public interface UserRepository {
	User save(User user);
}
