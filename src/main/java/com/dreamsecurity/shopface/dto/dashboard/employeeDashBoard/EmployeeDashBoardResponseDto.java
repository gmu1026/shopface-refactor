package com.dreamsecurity.shopface.dto.dashboard.employeeDashBoard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class EmployeeDashBoardResponseDto {
    private Long scheduleNo;
    private LocalDateTime workStartTime;
    private LocalDateTime workEndTime;
    private String branchName;
    private String occupationName;
    private LocalDateTime workingTime;
    private LocalDateTime quittingTime;

    public EmployeeDashBoardResponseDto(
            Long scheduleNo, LocalDateTime workStartTime, LocalDateTime workEndTime,
            String branchName, String occupationName) {
        this.scheduleNo = scheduleNo;
        this.workStartTime = workStartTime;
        this.workEndTime = workEndTime;
        this.branchName = branchName;
        this.occupationName = occupationName;
    }
}
