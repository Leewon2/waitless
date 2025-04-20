package com.waitless.benefit.point.application.mapper;

import com.waitless.benefit.point.application.dto.command.PostPointCommand;
import com.waitless.benefit.point.domain.entity.Point;
import com.waitless.benefit.point.domain.vo.PointAmount;
import com.waitless.benefit.point.domain.vo.PointType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PointServiceMapper {
    default Point toEntity(PostPointCommand command) {
        return Point.of(
                command.userId(),
                command.reviewId(),
                command.reservationId(),
                PointAmount.of(command.amount()),
                PointType.of(command.pointType()),
                command.description()
        );
    }
}
