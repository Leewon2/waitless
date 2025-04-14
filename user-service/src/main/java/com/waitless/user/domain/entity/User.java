package com.waitless.user.domain.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Where;

import com.waitless.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted=false")
// @Filter(name = "deletedFilter", condition = "(deleted_at IS NOT NULL) = :isDeleted")
public class User  extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String phone;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;

	@Column(nullable = false, unique = true)
	private String slackId;

	public User(String email, String password, String name, String phone, Role role, String slackId) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.phone = phone;
		this.role = role;
		this.slackId = slackId;
	}

	public void modifyUserInfo(String key, Object value) {
		switch (key) {
			case "name" -> this.name = (String) value;
			case "phone" -> this.phone = (String) value;
			case "slackId" -> this.slackId = (String) value;
			default -> throw new IllegalArgumentException("잘못된 필드명 : " + key);
		}
	}
}
