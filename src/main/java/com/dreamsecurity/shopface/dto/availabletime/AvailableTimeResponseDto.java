package com.dreamsecurity.shopface.dto.availabletime;

import com.dreamsecurity.shopface.domain.AvailableTime;
import com.dreamsecurity.shopface.domain.Branch;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AvailableTimeResponseDto {
    // TODO 일단은 가용시간은 Branch 이동이 자유롭게 설계
    private Branch branch;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public AvailableTimeResponseDto(AvailableTime entity) {
        this.branch = entity.getBranch();
        this.startTime = entity.getStartTime();
        this.endTime = entity.getEndTime();
    }
}
