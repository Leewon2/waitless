package com.waitless.payment.application.service;

import org.springframework.stereotype.Service;

import com.waitless.payment.application.dto.MakePaymentDto;
import com.waitless.payment.application.dto.PaymentResponseDto;
import com.waitless.payment.domain.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

	private final PaymentRepository paymentRepository;

	@Override
	public PaymentResponseDto makePayment(MakePaymentDto makePaymentDto) {
		return null;
	}
}
