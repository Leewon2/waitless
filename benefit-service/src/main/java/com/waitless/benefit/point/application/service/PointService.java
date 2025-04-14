package com.waitless.benefit.point.application.service;

import com.waitless.benefit.point.application.dto.command.PostPointCommand;
import com.waitless.benefit.point.application.dto.result.PostPointResult;

public interface PointService {
    PostPointResult createPoint(PostPointCommand command);
}
