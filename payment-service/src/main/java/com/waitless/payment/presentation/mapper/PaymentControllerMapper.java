package com.waitless.payment.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.waitless.payment.application.dto.MakePaymentDto;
import com.waitless.payment.presentation.dto.MakePaymentRequestDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentControllerMapper {

	MakePaymentDto toMakePaymentDto(MakePaymentRequestDto makePaymentRequestDto);
}
