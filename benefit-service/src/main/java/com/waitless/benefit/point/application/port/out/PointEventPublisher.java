package com.waitless.benefit.point.application.port.out;

import com.waitless.common.event.PointIssuedEvent;

public interface PointEventPublisher {
    void publishPointIssued(PointIssuedEvent event);
}
