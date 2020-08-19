package com.dreamsecurity.shopface.dto.dashboard.businessmanDashBoard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ToString
public class BusinessmanDashBoardListResponseDto {
    private Long employNo;
    private String employName;
    private Long employSalary;
    private String occupationName;
    private LocalDateTime workStartTime;
    private LocalDateTime workEndTime;
    private double hoursPlan;
    private double salaryPlan;
    private LocalDateTime workingTime;
    private LocalDateTime quittingTime;
    private double actualWorkingHours;
    private double actualSalary;
    private String scheduleStatus;

    public BusinessmanDashBoardListResponseDto(Long employNo,
                                               String employName,
                                               Long employSalary,
                                               String occupationName,
                                               LocalDateTime workStartTime,
                                               LocalDateTime workEndTime,
                                               LocalDateTime workingTime,
                                               LocalDateTime quittingTime,
                                               String scheduleStatus) {
        this.employNo = employNo;
        this.employName = employName;
        this.employSalary = employSalary;
        this.occupationName = occupationName;
        this.workStartTime = workStartTime;
        this.workEndTime = workEndTime;
        this.workingTime = workingTime;
        this.quittingTime = quittingTime;
        this.scheduleStatus = scheduleStatus;
    }

    public BusinessmanDashBoardListResponseDto(Long employNo,
                                               String employName,
                                               Long employSalary,
                                               String occupationName,
                                               LocalDateTime workStartTime,
                                               LocalDateTime workEndTime,
                                               String scheduleStatus) {
        this.employNo = employNo;
        this.employName = employName;
        this.employSalary = employSalary;
        this.occupationName = occupationName;
        this.workStartTime = workStartTime;
        this.workEndTime = workEndTime;
        this.scheduleStatus = scheduleStatus;
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
