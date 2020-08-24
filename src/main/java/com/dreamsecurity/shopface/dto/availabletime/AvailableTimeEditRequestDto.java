package com.dreamsecurity.shopface.dto.availabletime;

import com.dreamsecurity.shopface.domain.AvailableTime;
import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AvailableTimeEditRequestDto {
    private Branch branch;
    private Member member;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public AvailableTimeEditRequestDto(AvailableTime entity) {
        this.member = entity.getMember();
        this.branch = entity.getBranch();
        this.startTime = entity.getStartTime();
        this.endTime = entity.getEndTime();
    }

    public AvailableTime toEntity() {
        return AvailableTime.builder()
                .member(this.member)
                .branch(this.branch)
                .startTime(this.startTime)
                .endTime(this.endTime)
                .build();
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
