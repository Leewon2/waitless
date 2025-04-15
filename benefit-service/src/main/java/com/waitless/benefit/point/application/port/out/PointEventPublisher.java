package com.waitless.benefit.point.application.port.out;

import com.waitless.common.event.PointIssuedEvent;
import com.waitless.common.event.PointIssuedFailedEvent;

public interface PointEventPublisher {
    void publishPointIssued(PointIssuedEvent event);
    void publishPointIssuedFailed(PointIssuedFailedEvent event);
}
