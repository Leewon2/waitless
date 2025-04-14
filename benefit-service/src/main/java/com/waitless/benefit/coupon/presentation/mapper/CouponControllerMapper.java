package com.waitless.benefit.coupon.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.waitless.benefit.coupon.application.dto.CreateCouponDto;
import com.waitless.benefit.coupon.presentation.dto.CreateCouponRequestDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CouponControllerMapper {
	CreateCouponDto toCreateCouponDto(CreateCouponRequestDto createCouponRequestDto);
}
