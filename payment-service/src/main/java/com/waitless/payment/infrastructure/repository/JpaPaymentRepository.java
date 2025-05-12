package com.waitless.payment.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waitless.payment.domain.entity.Payment;

public interface JpaPaymentRepository extends JpaRepository<Payment, UUID> {
}
