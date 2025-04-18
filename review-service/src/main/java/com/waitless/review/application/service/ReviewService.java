package com.waitless.review.application.service;

import com.waitless.review.application.dto.command.DeleteReviewCommand;
import com.waitless.review.application.dto.command.PageCommand;
import com.waitless.review.application.dto.command.PostReviewCommand;
import com.waitless.review.domain.vo.ReviewSearchCondition;
import com.waitless.review.application.dto.result.*;
import org.springframework.data.domain.Pageable;

public interface ReviewService {
    PostReviewResult createReview(PostReviewCommand command);
    DeleteReviewResult deleteReview(DeleteReviewCommand command);
    GetReviewResult findOne(ReviewSearchCondition condition); // 단건 조회
    PageCommand<GetReviewListResult> findList(ReviewSearchCondition condition, Pageable pageable); // 리스트 조회
    PageCommand<SearchReviewsResult> findSearch(ReviewSearchCondition condition, Pageable pageable); // 조건 조회
}
