package com.waitless.benefit.coupon.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.waitless.benefit.coupon.application.dto.CouponResponseDto;
import com.waitless.benefit.coupon.domain.entity.Coupon;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CouponServiceMapper {

	@Mapping(target = "id", source = "id")
	CouponResponseDto toCouponResponseDto(Coupon coupon);
}
