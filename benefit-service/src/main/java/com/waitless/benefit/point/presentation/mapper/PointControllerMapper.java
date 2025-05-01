package com.waitless.benefit.point.presentation.mapper;

import com.waitless.benefit.point.application.dto.command.GetMyRankingCommand;
import com.waitless.benefit.point.application.dto.command.GetPointAmountCommand;
import com.waitless.benefit.point.application.dto.command.SearchRankingCommand;
import com.waitless.benefit.point.domain.vo.PointSearchCondition;
import com.waitless.benefit.point.presentation.dto.request.*;
import com.waitless.common.domain.UserInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PointControllerMapper {

    default PointSearchCondition toCondition(GetPointRequestDto requestDto) {
        return new PointSearchCondition(requestDto.reviewId(), null);
    }
    default PointSearchCondition toCondition(GetPointListRequestDto requestDto) {
        return new PointSearchCondition(null, requestDto.userId());
    }
    @Mapping(source = "userInfoDto.userId", target = "userId")
    @Mapping(source = "userInfoDto", target = "userInfoDto")
    GetPointAmountCommand toCommand(GetPointAmountRequestDto requestDto, UserInfoDto userInfoDto);
    SearchRankingCommand toCommand(SearchRankingRequestDto requestDto, UserInfoDto userInfoDto);
    @Mapping(source = "userInfoDto.userId", target = "userId")
    @Mapping(source = "userInfoDto", target = "userInfoDto")
    GetMyRankingCommand toCommand(GetMyRankingRequestDto requestDto, UserInfoDto userInfoDto);
}
