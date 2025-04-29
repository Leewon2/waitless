package com.waitless.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.waitless.common.domain.Role;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RoleCheck {
	Role[] roles() default { Role.ADMIN, Role.OWNER, Role.USER };
}

