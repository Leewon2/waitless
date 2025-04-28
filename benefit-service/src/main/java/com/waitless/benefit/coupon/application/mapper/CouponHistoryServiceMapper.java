package com.waitless.benefit.coupon.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.waitless.benefit.coupon.application.dto.CouponHistoryCacheDto;
import com.waitless.benefit.coupon.application.dto.CouponHistoryResponseDto;
import com.waitless.benefit.coupon.domain.entity.CouponHistory;

@Mapper(componentModel = "spring")
public interface CouponHistoryServiceMapper {

	@Mapping(target = "id", source = "id")
	CouponHistoryResponseDto toCouponHistoryResponseDto(CouponHistory couponHistory);

	CouponHistory toCouponHistory(CouponHistoryCacheDto couponHistoryCacheDto);

	CouponHistoryResponseDto toCouponHistoryResponseDto(CouponHistoryCacheDto couponHistoryCacheDto);
}
