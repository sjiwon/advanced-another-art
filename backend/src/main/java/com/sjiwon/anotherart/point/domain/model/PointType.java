package com.sjiwon.anotherart.point.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PointType {
    CHARGE("포인트 충전"),
    REFUND("포인트 환불"),
    PURCHASE("작품 구매"),
    SOLD("작품 판매"),
    ;

    private final String description;
}
