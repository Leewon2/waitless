package com.waitless.benefit.point.presentation.dto.request;

public record SearchRankingRequestDto(
        int topN // default는 controller에서 설정 가능 (ex. 10)
) {}