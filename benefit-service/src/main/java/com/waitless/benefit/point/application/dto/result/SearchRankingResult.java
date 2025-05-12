package com.waitless.benefit.point.application.dto.result;

import com.waitless.benefit.point.application.dto.command.PageCommand;
import lombok.Builder;
import org.springframework.data.domain.Page;

@Builder
public record SearchRankingResult(
        Long userId,
        int totalPoint,
        int rank
) {
    public static SearchRankingResult of(Long userId, int totalPoint, int rank) {
        return new SearchRankingResult(userId, totalPoint, rank);
    }
    public static PageCommand<SearchRankingResult> toPageCommand(Page<SearchRankingResult> page) {
        return PageCommand.from(page);
    }
}
