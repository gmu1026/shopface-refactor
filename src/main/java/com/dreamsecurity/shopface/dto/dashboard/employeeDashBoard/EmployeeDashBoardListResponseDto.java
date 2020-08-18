package com.dreamsecurity.shopface.dto.dashboard.employeeDashBoard;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class EmployeeDashBoardListResponseDto {
    //예정 스케줄
    private String branchName;
    private String occupationName;
    private Long employSalsry;
    private LocalDateTime workStartTime;
    private LocalDateTime workEndTime;
    private double hoursPlan;
    private double salaryPlan;
    private LocalDateTime workingTime;
    private LocalDateTime quittingTime;
    private double actualWorkingHours;
    private double actualSalary;
    private String division;

    public EmployeeDashBoardListResponseDto (String branchName,
                                              String occupationName,
                                              Long employSalsry,
                                              LocalDateTime workStartTime,
                                              LocalDateTime workEndTime,
                                              LocalDateTime workingTime,
                                              LocalDateTime quittingTime) {
        this.branchName = branchName;
        this.occupationName = occupationName;
        this.employSalsry = employSalsry;
        this.workStartTime = workStartTime;
        this.workEndTime = workEndTime;
        this.workingTime = workingTime;
        this.quittingTime = quittingTime;
    }

    public EmployeeDashBoardListResponseDto (String branchName,
                                             String occupationName,
                                             Long employSalsry,
                                             LocalDateTime workStartTime,
                                             LocalDateTime workEndTime) {
        this.branchName = branchName;
        this.occupationName = occupationName;
        this.employSalsry = employSalsry;
        this.workStartTime = workStartTime;
        this.workEndTime = workEndTime;
    }

    public void setHoursPlan(double hoursPlan) {
        this.hoursPlan = hoursPlan;
    }

    public void setSalaryPlan(double salaryPlan) {
        this.salaryPlan = salaryPlan;
    }

    public void setActualWorkingHours(double actualWorkingHours) {
        this.actualWorkingHours = actualWorkingHours;
    }

    public void setActualSalary(double actualSalary) {
        this.actualSalary = actualSalary;
    }
}
