package com.dreamsecurity.shopface.dto.dashboard.businessmanDashBoard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BusinessmanDashBoardListResponseDto {
    private Long employNo;
    private String employName;
    private Long employSalsry;
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
                                               Long employSalsry,
                                               String occupationName,
                                               LocalDateTime workStartTime,
                                               LocalDateTime workEndTime,
                                               LocalDateTime workingTime,
                                               LocalDateTime quittingTime,
                                               String scheduleStatus) {
        this.employNo = employNo;
        this.employName = employName;
        this.employSalsry = employSalsry;
        this.occupationName = occupationName;
        this.workStartTime = workStartTime;
        this.workEndTime = workEndTime;
//        this.hoursPlan = hoursPlan;
//        this.salaryPlan = salaryPlan;
        this.workingTime = workingTime;
        this.quittingTime = quittingTime;
//        this.actualWorkingHours = actualWorkingHours;
//        this.actualSalary = actualSalary;
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
