package com.dreamsecurity.shopface.dto.availabletime;

import com.dreamsecurity.shopface.domain.AvailableTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AvailableTimeListResponseDto {
    private Long no;
    private Long branchNo;
    private String memberId;
    private String memberName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public AvailableTimeListResponseDto(AvailableTime entity) {
        this.no = entity.getNo();
        this.branchNo = entity.getBranch().getNo();
        this.memberId = entity.getMember().getId();
        this.memberName = entity.getMember().getName();
        this.startTime = entity.getStartTime();
        this.endTime = entity.getEndTime();
    }
}
