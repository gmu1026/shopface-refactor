package com.dreamsecurity.shopface.dto.dashboard.businessmanDashBoard;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BusinessmanDashBoardListRequestDto {
    private String memberId;
    private Long branchNo;
    private String scheduleStatus;

    @Builder
    BusinessmanDashBoardListRequestDto (String memberId, Long branchNo, String scheduleStatus) {
        this.branchNo = branchNo;
        this.memberId = memberId;
        this.scheduleStatus = scheduleStatus;
    }
}
