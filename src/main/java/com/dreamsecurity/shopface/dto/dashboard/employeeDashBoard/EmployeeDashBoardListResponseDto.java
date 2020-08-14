package com.dreamsecurity.shopface.dto.dashboard.employeeDashBoard;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EmployeeDashBoardListResponseDto {
    //예정 스케줄
    private Long branchNo;
    private String branchName;
    private String occupationName;
    private LocalDateTime workStartTime;
    private LocalDateTime workEndTime;
    private Long hoursPlan;
    private Long salaryPlan;
    private LocalDateTime workingTime;
    private LocalDateTime quittingTime;
    private Long actualWorkingHours;
    private Long actualSalary;
    private String division;





}
