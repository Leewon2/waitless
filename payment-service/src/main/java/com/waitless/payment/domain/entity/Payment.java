package com.waitless.payment.domain.entity;

import java.util.UUID;

import org.hibernate.annotations.Where;

import com.waitless.common.domain.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "p_payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted=false")
@Builder
public class Payment extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id")
	private UUID id;

	@Column
	private int price;

	@Column(nullable = false)
	private int discountedPrice;

	public Payment(UUID id, int price, int discountedPrice) {
		this.id = id;
		this.price = price;
		this.discountedPrice = discountedPrice;
	}
}
