package com.waitless.benefit.point.presentation.controller;

import com.waitless.benefit.point.application.dto.command.GetMyRankingCommand;
import com.waitless.benefit.point.application.dto.command.GetPointAmountCommand;
import com.waitless.benefit.point.application.dto.command.SearchRankingCommand;
import com.waitless.benefit.point.application.service.PointQueryService;
import com.waitless.benefit.point.domain.vo.PointSearchCondition;
import com.waitless.benefit.point.presentation.dto.request.*;
import com.waitless.benefit.point.presentation.dto.response.*;
import com.waitless.benefit.point.presentation.mapper.PointControllerMapper;
import com.waitless.common.aop.RoleCheck;
import com.waitless.common.domain.Role;
import com.waitless.common.domain.UserInfo;
import com.waitless.common.domain.UserInfoDto;
import com.waitless.common.exception.response.SingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/points")
public class PointExternalController {

    private final PointQueryService pointQueryService;
    private final PointControllerMapper pointControllerMapper;

    @RoleCheck(roles = {Role.ADMIN, Role.USER})
    @GetMapping("/{reviewId}")
    public ResponseEntity<SingleResponse<GetPointResponseDto>> getPoint(
            @PathVariable("reviewId") UUID reviewId,
            @UserInfo UserInfoDto userInfoDto
    ) {
        GetPointRequestDto requestDto = new GetPointRequestDto(reviewId);
        PointSearchCondition condition = pointControllerMapper.toCondition(requestDto);
        GetPointResponseDto responseDto = GetPointResponseDto.from(
                pointQueryService.findOne(condition)
        );
        return ResponseEntity.ok(SingleResponse.success(responseDto));
    }

    @RoleCheck(roles = {Role.ADMIN, Role.USER})
    @GetMapping("/list")
    public ResponseEntity<SingleResponse<GetPointListResponseDto>> getPointList(
            @ModelAttribute GetPointListRequestDto requestDto,
            Pageable pageable,
            @UserInfo UserInfoDto userInfoDto
    ) {
        PointSearchCondition condition = pointControllerMapper.toCondition(requestDto);
        GetPointListResponseDto responseDto = GetPointListResponseDto.from(
                pointQueryService.findList(condition, pageable)
        );
        return ResponseEntity.ok(SingleResponse.success(responseDto));
    }

    @RoleCheck(roles = {Role.ADMIN, Role.USER})
    @GetMapping("/amount")
    public ResponseEntity<SingleResponse<GetPointAmountResponseDto>> getTotalPoint(
            @ModelAttribute GetPointAmountRequestDto requestDto,
            @UserInfo UserInfoDto userInfoDto
    ) {
        GetPointAmountCommand command = pointControllerMapper.toCommand(requestDto, userInfoDto);
        GetPointAmountResponseDto responseDto = GetPointAmountResponseDto.from(
                pointQueryService.getTotalAmount(command)
        );
        return ResponseEntity.ok(SingleResponse.success(responseDto));
    }

    @RoleCheck(roles = {Role.ADMIN, Role.USER})
    @GetMapping("/ranking/top")
    public ResponseEntity<SingleResponse<SearchRankingResponseDto>> getTopRanking(
            @ModelAttribute SearchRankingRequestDto requestDto,
            @UserInfo UserInfoDto userInfoDto
    ) {
        SearchRankingCommand command = pointControllerMapper.toCommand(requestDto, userInfoDto);
        SearchRankingResponseDto responseDto = SearchRankingResponseDto.from(
                pointQueryService.getTopRanking(command)
        );
        return ResponseEntity.ok(SingleResponse.success(responseDto));
    }

    @RoleCheck(roles = {Role.ADMIN, Role.USER})
    @GetMapping("/ranking/me")
    public ResponseEntity<SingleResponse<GetMyRankingResponseDto>> getMyRanking(
            @ModelAttribute GetMyRankingRequestDto requestDto,
            @UserInfo UserInfoDto userInfoDto
    ) {
        GetMyRankingCommand command = pointControllerMapper.toCommand(requestDto, userInfoDto);
        GetMyRankingResponseDto responseDto = GetMyRankingResponseDto.from(
                pointQueryService.getMyRanking(command)
        );
        return ResponseEntity.ok(SingleResponse.success(responseDto));
    }
}
