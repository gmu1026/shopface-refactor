package com.dreamsecurity.shopface.dto.dashboard.employeeDashBoard;

import com.dreamsecurity.shopface.domain.Employ;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmployeeDashBoardListRequestDto {
    private String memberId;
    private String state;

    @Builder
    EmployeeDashBoardListRequestDto (String memberId, String state) {
        this.memberId = memberId;
        this.state = state;
    }
}
