package com.dreamsecurity.shopface.dto.availabletime;

import com.dreamsecurity.shopface.domain.AvailableTime;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AvailableTimeListResponseDto {
    //TODO 사업자와 근무자간 리스트가 상이함
    // 사업자: 사업장에 속한 근무자들의 가용시간표 근무자명이 필요함
    // 근무자: 자기자신의 가용시간표 (같은 지점 내 다른 근무자들의 가용시간도 알아야 스케줄을 교환 요청할 수 있다)
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public AvailableTimeListResponseDto(AvailableTime entity) {
        this.startTime = entity.getStartTime();
        this.endTime = entity.getEndTime();
    }
}
