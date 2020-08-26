package com.dreamsecurity.shopface.dto.availabletime;

import com.dreamsecurity.shopface.domain.AvailableTime;
import com.dreamsecurity.shopface.domain.Branch;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AvailableTimeResponseDto {
    private Branch branch;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public AvailableTimeResponseDto(AvailableTime entity) {
        this.branch = entity.getBranch();
        this.startTime = entity.getStartTime();
        this.endTime = entity.getEndTime();
    }
}
