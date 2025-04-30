package com.waitless.benefit.point.domain.vo;

import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointRankingCache implements Serializable {
    private Long userId;
    private int totalPoint;
    private int rank;
}