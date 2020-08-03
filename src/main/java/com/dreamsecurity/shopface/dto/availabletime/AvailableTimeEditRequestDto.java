package com.dreamsecurity.shopface.dto.availabletime;

import com.dreamsecurity.shopface.domain.AvailableTime;
import com.dreamsecurity.shopface.domain.Branch;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AvailableTimeEditRequestDto {
    private Branch branch;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public AvailableTimeEditRequestDto(AvailableTime entity) {
        this.branch = entity.getBranch();
        this.startTime = entity.getStartTime();
        this.endTime = entity.getEndTime();
    }

    public AvailableTime toEntity() {
        return AvailableTime.builder()
                .branch(this.branch)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .build();
    }
}
