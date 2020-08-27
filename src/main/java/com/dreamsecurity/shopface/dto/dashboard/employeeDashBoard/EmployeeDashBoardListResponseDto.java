package com.dreamsecurity.shopface.dto.dashboard.employeeDashBoard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class EmployeeDashBoardListResponseDto {
    //예정 스케줄
    private String branchName;
    private String occupationName;
    private Long employSalary;
    private Long scheduleNo;
    private LocalDateTime workStartTime;
    private LocalDateTime workEndTime;
    private double hoursPlan;
    private double salaryPlan;
    private LocalDateTime workingTime;
    private LocalDateTime quittingTime;
    private double actualWorkingHours;
    private double actualSalary;
    private String state;

    public EmployeeDashBoardListResponseDto (String branchName,
                                             String occupationName,
                                             Long employSalary,
                                             Long scheduleNo,
                                             LocalDateTime workStartTime,
                                             LocalDateTime workEndTime,
                                             LocalDateTime workingTime,
                                             LocalDateTime quittingTime,
                                             String state) {
        this.branchName = branchName;
        this.occupationName = occupationName;
        this.employSalary = employSalary;
        this.scheduleNo = scheduleNo;
        this.workStartTime = workStartTime;
        this.workEndTime = workEndTime;
        this.workingTime = workingTime;
        this.quittingTime = quittingTime;
        this.state = state;
    }

    public EmployeeDashBoardListResponseDto (String branchName,
                                             String occupationName,
                                             Long employSalary,
                                             Long scheduleNo,
                                             LocalDateTime workStartTime,
                                             LocalDateTime workEndTime,
                                             String state) {
        this.branchName = branchName;
        this.occupationName = occupationName;
        this.employSalary = employSalary;
        this.scheduleNo = scheduleNo;
        this.workStartTime = workStartTime;
        this.workEndTime = workEndTime;
        this.state = state;
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
