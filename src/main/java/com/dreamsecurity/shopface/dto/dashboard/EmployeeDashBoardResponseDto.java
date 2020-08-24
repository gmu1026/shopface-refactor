package com.dreamsecurity.shopface.dto.dashboard;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class EmployeeDashBoardResponseDto {
    private Long no;
    private LocalDateTime workStartTime;
    private LocalDateTime workEndTime;
    private String branchName;
    private String occupationName;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime workingTime;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LocalDateTime quittingTime;
//    private Long hours;
//    private Long minutes;

    public EmployeeDashBoardResponseDto(
            Long no, LocalDateTime workStartTime, LocalDateTime workEndTime,
            String branchName, String occupationName) {
        this.no = no;
        this.workStartTime = workStartTime;
        this.workEndTime = workEndTime;
        this.branchName = branchName;
        this.occupationName = occupationName;
    }
}
