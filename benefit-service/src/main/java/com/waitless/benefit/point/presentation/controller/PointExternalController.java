package com.waitless.benefit.point.presentation.controller;

import com.waitless.benefit.point.application.dto.command.GetMyRankingCommand;
import com.waitless.benefit.point.application.dto.command.GetPointAmountCommand;
import com.waitless.benefit.point.application.dto.command.SearchRankingCommand;
import com.waitless.benefit.point.application.service.PointQueryService;
import com.waitless.benefit.point.domain.vo.PointSearchCondition;
import com.waitless.benefit.point.presentation.dto.request.*;
import com.waitless.benefit.point.presentation.dto.response.*;
import com.waitless.benefit.point.presentation.mapper.PointControllerMapper;
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

    @GetMapping("/{reviewId}")
    public ResponseEntity<SingleResponse<GetPointResponseDto>> getPoint(
            @PathVariable("reviewId") UUID reviewId
    ) {
        GetPointRequestDto requestDto = new GetPointRequestDto(reviewId);
        PointSearchCondition condition = pointControllerMapper.toCondition(requestDto);
        GetPointResponseDto responseDto = GetPointResponseDto.from(
                pointQueryService.findOne(condition)
        );
        return ResponseEntity.ok(SingleResponse.success(responseDto));
    }

    @GetMapping("/list")
    public ResponseEntity<SingleResponse<GetPointListResponseDto>> getPointList(
            @ModelAttribute GetPointListRequestDto requestDto,
            Pageable pageable
    ) {
        PointSearchCondition condition = pointControllerMapper.toCondition(requestDto);
        GetPointListResponseDto responseDto = GetPointListResponseDto.from(
                pointQueryService.findList(condition, pageable)
        );
        return ResponseEntity.ok(SingleResponse.success(responseDto));
    }

    @GetMapping("/amount")
    public ResponseEntity<SingleResponse<GetPointAmountResponseDto>> getTotalPoint(
            @ModelAttribute GetPointAmountRequestDto requestDto
    ) {
        GetPointAmountCommand command = pointControllerMapper.toCommand(requestDto);
        GetPointAmountResponseDto responseDto = GetPointAmountResponseDto.from(
                pointQueryService.getTotalAmount(command)
        );
        return ResponseEntity.ok(SingleResponse.success(responseDto));
    }

    @GetMapping("/ranking/top")
    public ResponseEntity<SingleResponse<SearchRankingResponseDto>> getTopRanking(
            @ModelAttribute SearchRankingRequestDto requestDto
    ) {
        SearchRankingCommand command = pointControllerMapper.toCommand(requestDto);
        SearchRankingResponseDto responseDto = SearchRankingResponseDto.from(
                pointQueryService.getTopRanking(command)
        );
        return ResponseEntity.ok(SingleResponse.success(responseDto));
    }

    @GetMapping("/ranking/me")
    public ResponseEntity<SingleResponse<GetMyRankingResponseDto>> getMyRanking(
            @ModelAttribute GetMyRankingRequestDto requestDto
    ) {
        GetMyRankingCommand command = pointControllerMapper.toCommand(requestDto);
        GetMyRankingResponseDto responseDto = GetMyRankingResponseDto.from(
                pointQueryService.getMyRanking(command)
        );
        return ResponseEntity.ok(SingleResponse.success(responseDto));
    }
}
