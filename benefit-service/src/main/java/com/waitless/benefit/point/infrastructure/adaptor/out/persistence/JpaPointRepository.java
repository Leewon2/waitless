package com.waitless.benefit.point.infrastructure.adaptor.out.persistence;

import com.waitless.benefit.point.domain.entity.Point;
import com.waitless.benefit.point.domain.repository.PointRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaPointRepository extends JpaRepository<Point, UUID>, PointRepository {
}
