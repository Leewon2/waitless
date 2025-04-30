package com.waitless.benefit.point.application.service;

import com.waitless.benefit.point.application.dto.command.GetMyRankingCommand;
import com.waitless.benefit.point.application.dto.command.GetPointAmountCommand;
import com.waitless.benefit.point.application.dto.command.PageCommand;
import com.waitless.benefit.point.application.dto.command.SearchRankingCommand;
import com.waitless.benefit.point.application.dto.result.*;
import com.waitless.benefit.point.domain.vo.PointSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PointQueryService {
    GetPointResult findOne(PointSearchCondition condition); // 단건 조회 (reviewId 기준)
    PageCommand<GetPointListResult> findList(PointSearchCondition condition, Pageable pageable); // 리스트 조회 (userId 기준)
    GetPointAmountResult getTotalAmount(GetPointAmountCommand command); // 유저 총 포인트 조회
    PageCommand<SearchRankingResult> getTopRanking(SearchRankingCommand command); // Top N 유저 랭킹 조회
    GetMyRankingResult getMyRanking(GetMyRankingCommand command); // 본인 랭킹 조회
}
