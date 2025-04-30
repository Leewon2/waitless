package com.waitless.benefit.point.presentation.mapper;

import com.waitless.benefit.point.application.dto.command.GetMyRankingCommand;
import com.waitless.benefit.point.application.dto.command.GetPointAmountCommand;
import com.waitless.benefit.point.application.dto.command.SearchRankingCommand;
import com.waitless.benefit.point.domain.vo.PointSearchCondition;
import com.waitless.benefit.point.presentation.dto.request.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PointControllerMapper {

    default PointSearchCondition toCondition(GetPointRequestDto requestDto) {
        return new PointSearchCondition(requestDto.reviewId(), null);
    }
    default PointSearchCondition toCondition(GetPointListRequestDto requestDto) {
        return new PointSearchCondition(null, requestDto.userId());
    }
    GetPointAmountCommand toCommand(GetPointAmountRequestDto requestDto);
    SearchRankingCommand toCommand(SearchRankingRequestDto requestDto);
    GetMyRankingCommand toCommand(GetMyRankingRequestDto requestDto);
}
