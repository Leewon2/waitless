package com.waitless.benefit.point.application.port.out;

import com.waitless.common.event.PointIssuedEvent;

public interface PointOutboxPort {
    void savePointIssuedEvent(PointIssuedEvent event);
}
