package com.waitless.benefit.point.application.port.in;

import com.waitless.benefit.point.application.dto.command.PostPointCommand;
import com.waitless.benefit.point.application.dto.result.PostPointResult;

public interface PointCommandUseCase {
    PostPointResult createPoint(PostPointCommand command);
}
