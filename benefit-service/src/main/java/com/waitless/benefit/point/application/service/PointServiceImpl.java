package com.waitless.benefit.point.application.service;

import com.waitless.benefit.point.application.dto.command.PostPointCommand;
import com.waitless.benefit.point.application.dto.result.PostPointResult;
import com.waitless.benefit.point.application.mapper.PointServiceMapper;
import com.waitless.benefit.point.application.port.out.PointOutboxPort;
import com.waitless.benefit.point.domain.entity.Point;
import com.waitless.benefit.point.domain.repository.PointRepository;
import com.waitless.common.event.PointIssuedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService{

    private final PointRepository pointRepository;
    private final PointServiceMapper pointServiceMapper;
    private final PointOutboxPort pointOutboxPort;

    @Override
    @Transactional
    public PostPointResult createPoint(PostPointCommand command) {
        Point point = pointServiceMapper.toEntity(command);
        Point saved = pointRepository.save(point);

        PointIssuedEvent event = PointIssuedEvent.builder()
                .pointId(saved.getId())
                .userId(saved.getUserId())
                .amount(saved.getAmount().getPointValue())
                .description(saved.getDescription())
                .issuedAt(saved.getCreatedAt())
                .build();
        pointOutboxPort.savePointIssuedEvent(event);
        return PostPointResult.from(saved);
    }
}
