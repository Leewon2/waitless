package com.waitless.review.presentation.mapper;

import com.waitless.review.application.dto.command.DeleteReviewCommand;
import com.waitless.review.application.dto.command.PostReviewCommand;
import com.waitless.review.application.dto.command.ReviewStatisticsCommand;
import com.waitless.review.domain.vo.ReviewSearchCondition;
import com.waitless.review.presentation.dto.request.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewControllerMapper {
    PostReviewCommand toCommand(PostReviewRequestDto requestDto);
    DeleteReviewCommand toCommand(DeleteReviewRequestDto requestDto);
    default ReviewSearchCondition toCondition(GetReviewRequestDto requestDto) {
        return new ReviewSearchCondition(requestDto.reviewId(), null, null, null);
    }
    default ReviewSearchCondition toCondition(GetReviewListRequestDto requestDto) {
        return new ReviewSearchCondition(null, requestDto.userId(), requestDto.restaurantId(), null);
    }
    default ReviewSearchCondition toCondition(SearchReviewsRequestDto requestDto) {
        return new ReviewSearchCondition(null, requestDto.userId(), requestDto.restaurantId(), requestDto.rating());
    }
    default ReviewStatisticsCommand toCommand(ReviewStatisticsBatchRequestDto requestDto) {
        return new ReviewStatisticsCommand(requestDto.restaurantIds());
    }
}
