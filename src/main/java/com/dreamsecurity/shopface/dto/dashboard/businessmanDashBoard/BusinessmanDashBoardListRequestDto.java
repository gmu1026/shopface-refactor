package com.dreamsecurity.shopface.dto.dashboard.businessmanDashBoard;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BusinessmanDashBoardListRequestDto {
    private String businessmanId;
    private Long branchNo;
    private String scheduleStatus;

    @Builder
    BusinessmanDashBoardListRequestDto (String businessmanId, Long branchNo, String scheduleStatus) {
        this.branchNo = branchNo;
        this.businessmanId = businessmanId;
        this.scheduleStatus = scheduleStatus;
    }
}
