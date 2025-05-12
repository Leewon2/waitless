package com.waitless.benefit.point.application.port.out;

import com.waitless.common.event.PointIssuedEvent;
import com.waitless.common.event.PointIssuedFailedEvent;

public interface PointOutboxPort {
    void savePointIssuedEvent(PointIssuedEvent event);
    void savePointIssuedFailedEvent(PointIssuedFailedEvent event);
}

