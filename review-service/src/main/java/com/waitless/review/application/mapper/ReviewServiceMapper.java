package com.waitless.review.application.mapper;

import com.waitless.review.application.dto.command.PostReviewCommand;
import com.waitless.review.domain.entity.Review;
import com.waitless.review.domain.vo.Rating;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewServiceMapper {
    default Review toEntity(PostReviewCommand command) {
        return Review.of(
                command.reservationId(),
                command.userId(),
                command.restaurantId(),
                command.content(),
                Rating.of(command.rating())
        );
    }
}
