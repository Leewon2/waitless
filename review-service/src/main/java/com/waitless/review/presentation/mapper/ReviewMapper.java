package com.waitless.review.presentation.mapper;

import com.waitless.review.application.dto.command.PostReviewCommand;
import com.waitless.review.presentation.dto.request.PostReviewRequestDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    PostReviewCommand toCommand(PostReviewRequestDto requestDto);
}
