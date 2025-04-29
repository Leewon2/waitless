package com.waitless.common.aop;

import java.util.Set;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.waitless.common.domain.Role;
import com.waitless.common.exception.BusinessException;
import com.waitless.common.exception.code.CommonErrorCode;

import jakarta.servlet.http.HttpServletRequest;

@Aspect
@Component
public class RoleCheckAspect {
	private static final String USER_ROLE = "X-User-Role";

	@Before("@annotation(roleCheck)")
	public void roleCheck(RoleCheck roleCheck) {
		Set<Role> roles = Set.of(roleCheck.roles());
		Role role = getUserRole();
		if(!roles.contains(role)) {
			throw BusinessException.from(CommonErrorCode.UNAUTHORIZED);
		}
	}

	private Role getUserRole() {
		ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		if (attributes == null) {
			throw BusinessException.from(CommonErrorCode.UNAUTHORIZED);
		}
		HttpServletRequest request = attributes.getRequest();
		String roleHeader = request.getHeader(USER_ROLE);
		if (roleHeader == null || roleHeader.isEmpty()) {
			throw BusinessException.from(CommonErrorCode.UNAUTHORIZED);
		}
		return Role.valueOf(roleHeader);
	}
}
