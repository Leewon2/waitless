package com.waitless.benefit.coupon.application.mapper;

import org.mapstruct.Mapper;

import com.waitless.benefit.coupon.application.dto.CouponHistoryResponseDto;
import com.waitless.benefit.coupon.domain.entity.CouponHistory;

@Mapper(componentModel = "spring")
public interface CouponHistoryServiceMapper {

	CouponHistoryResponseDto toCouponHistoryResponseDto(CouponHistory couponHistory);
}
