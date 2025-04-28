package com.waitless.payment.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.waitless.common.exception.response.SingleResponse;
import com.waitless.payment.application.dto.PaymentResponseDto;
import com.waitless.payment.application.service.PaymentService;
import com.waitless.payment.presentation.dto.MakePaymentRequestDto;
import com.waitless.payment.presentation.mapper.PaymentControllerMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

	private final PaymentService paymentService;
	private final PaymentControllerMapper paymentControllerMapper;

	@PostMapping
	public ResponseEntity<SingleResponse<PaymentResponseDto>> makePayment(@RequestBody MakePaymentRequestDto makePaymentRequestDto) {
		return ResponseEntity.ok(SingleResponse.success(paymentService.makePayment(paymentControllerMapper.toMakePaymentDto(makePaymentRequestDto))));
	}

}
