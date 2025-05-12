package com.waitless.payment.application.service;

import com.waitless.payment.application.dto.MakePaymentDto;
import com.waitless.payment.application.dto.PaymentResponseDto;

public interface PaymentService {
	PaymentResponseDto makePayment(MakePaymentDto makePaymentDto);
}
