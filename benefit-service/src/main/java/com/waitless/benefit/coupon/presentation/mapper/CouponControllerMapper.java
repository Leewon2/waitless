package com.waitless.benefit.coupon.presentation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.waitless.benefit.coupon.application.dto.CreateCouponDto;
import com.waitless.benefit.coupon.application.dto.ReadCouponHistoriesDto;
import com.waitless.benefit.coupon.presentation.dto.ReadCouponHistoriesRequestDto;
import com.waitless.benefit.coupon.application.dto.ReadCouponsDto;
import com.waitless.benefit.coupon.presentation.dto.CreateCouponRequestDto;
import com.waitless.benefit.coupon.presentation.dto.ReadCouponsRequestDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CouponControllerMapper {
	CreateCouponDto toCreateCouponDto(CreateCouponRequestDto createCouponRequestDto);

	ReadCouponsDto toReadCouponsDto(ReadCouponsRequestDto readCouponsRequestDto);

	ReadCouponHistoriesDto toReadCouponHistoriesDto(ReadCouponHistoriesRequestDto readCouponHistoriesRequestDto);
}
