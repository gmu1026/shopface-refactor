package com.dreamsecurity.shopface.dto.dashboard.businessmanDashBoard;

import java.time.LocalDateTime;

public class BusinessmanDashBoardListResponseDto {
    private Long memberNo;
    private String employName;
    private Long occupationNo;
    private String occupationName;
    private LocalDateTime workStartTime;
    private LocalDateTime workEndTime;
    private Long hoursPlan;
    private Long salaryPlan;
    private LocalDateTime workingTime;
    private String actualWorkingHours;
    private Long actualSalary;
    private String scheduleStatus;
}
