package com.waitless.payment.infrastructure.repository;

import org.springframework.stereotype.Repository;

import com.waitless.payment.domain.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

	private final JpaPaymentRepository jpaPaymentRepository;

}
