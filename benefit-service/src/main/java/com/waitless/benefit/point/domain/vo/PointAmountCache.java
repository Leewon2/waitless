package com.waitless.benefit.point.domain.vo;

import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointAmountCache implements Serializable {
    private int totalPoint;
}