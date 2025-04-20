package com.waitless.benefit.point.application.service;

import com.waitless.benefit.point.application.dto.command.PostPointCommand;
import com.waitless.benefit.point.application.dto.result.PostPointResult;
import com.waitless.benefit.point.application.mapper.PointServiceMapper;
import com.waitless.benefit.point.application.port.in.PointCommandUseCase;
import com.waitless.benefit.point.application.port.out.PointOutboxPort;
import com.waitless.benefit.point.domain.entity.Point;
import com.waitless.benefit.point.domain.repository.PointRepository;
import com.waitless.common.event.PointIssuedEvent;
import com.waitless.common.event.PointIssuedFailedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointCommandUseCase {

    private final PointRepository pointRepository;
    private final PointServiceMapper pointServiceMapper;
    private final PointOutboxPort pointOutboxPort;

    @Override
    @Transactional
    public PostPointResult createPoint(PostPointCommand command) {
        if(pointRepository.existsByUserIdAndReservationId(command.userId(), command.reservationId())) {
            log.warn("중복 포인트 적립 시도: userId={}, reservationId={}", command.userId(), command.reservationId());
            PointIssuedFailedEvent event = PointIssuedFailedEvent.builder()
                    .reviewId(command.reviewId())
                    .userId(command.userId())
                    .reservationId(command.reservationId())
                    .build();
            pointOutboxPort.savePointIssuedFailedEvent(event);
            throw new IllegalStateException("이미 적립된 리뷰 보상입니다.");
        }
        Point point = pointServiceMapper.toEntity(command);
        Point saved = pointRepository.save(point);

        PointIssuedEvent event = PointIssuedEvent.builder()
                .pointId(saved.getId())
                .userId(saved.getUserId())
                .reviewId(saved.getReviewId())
                .reservationId(saved.getReservationId())
                .amount(saved.getAmount().getPointValue())
                .description(saved.getDescription())
                .issuedAt(saved.getCreatedAt())
                .build();
        pointOutboxPort.savePointIssuedEvent(event);
        return PostPointResult.from(saved);
    }

    @Override
    @Transactional
    public void deletePointByReview(UUID reviewId, Long userId) {
        Point point = pointRepository.findByReviewIdAndUserId(reviewId, userId)
                .orElseThrow(() -> new IllegalStateException("해당 리뷰에 대한 포인트가 없습니다."));
        point.softDelete();
    }
}
