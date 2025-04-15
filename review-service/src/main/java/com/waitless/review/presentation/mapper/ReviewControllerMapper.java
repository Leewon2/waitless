package com.waitless.review.presentation.mapper;

import com.waitless.review.application.dto.command.DeleteReviewCommand;
import com.waitless.review.application.dto.command.PostReviewCommand;
import com.waitless.review.presentation.dto.request.DeleteReviewRequestDto;
import com.waitless.review.presentation.dto.request.PostReviewRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewControllerMapper {
    PostReviewCommand toCommand(PostReviewRequestDto requestDto);
    DeleteReviewCommand toCommand(DeleteReviewRequestDto requestDto);
}
