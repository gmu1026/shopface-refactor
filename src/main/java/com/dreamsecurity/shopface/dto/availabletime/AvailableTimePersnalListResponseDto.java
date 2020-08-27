package com.dreamsecurity.shopface.dto.availabletime;

import com.dreamsecurity.shopface.domain.AvailableTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AvailableTimePersnalListResponseDto {
    private Long no;
    private Long branchNo;
    private String memberId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public AvailableTimePersnalListResponseDto(AvailableTime entity) {
        this.no = entity.getNo();
        this.branchNo = entity.getBranch().getNo();
        this.memberId = entity.getMember().getId();
        this.startTime = entity.getStartTime();
        this.endTime = entity.getEndTime();
    }
}
