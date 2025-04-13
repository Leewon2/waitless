package com.waitless.benefit.point.application.service;

import com.waitless.benefit.point.application.dto.command.PostPointCommand;
import com.waitless.benefit.point.application.dto.result.PostPointResult;
import com.waitless.benefit.point.domain.entity.Point;
import com.waitless.benefit.point.domain.repository.PointRepository;
import com.waitless.benefit.point.domain.vo.PointAmount;
import com.waitless.benefit.point.domain.vo.PointType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService{

    private final PointRepository pointRepository;

    @Override
    @Transactional
    public PostPointResult createPoint(PostPointCommand command) {
        Point point = Point.of(
                command.userId(),
                PointAmount.of(command.amount()),
                PointType.of(command.pointType()),
                command.description()
        );
        Point saved = pointRepository.save(point);
        return PostPointResult.from(saved);
    }
}
