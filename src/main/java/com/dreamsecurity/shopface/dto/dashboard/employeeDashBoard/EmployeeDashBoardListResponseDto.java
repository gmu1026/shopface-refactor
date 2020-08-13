package com.dreamsecurity.shopface.dto.dashboard.employeeDashBoard;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EmployeeDashBoardListResponseDto {
    //예정 스케줄
    private Long branchNo;
    private String branchName;
    private Long occupationNo;
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
