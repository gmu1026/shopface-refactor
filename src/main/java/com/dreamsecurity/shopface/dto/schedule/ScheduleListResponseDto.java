package com.dreamsecurity.shopface.dto.schedule;

import com.dreamsecurity.shopface.domain.Schedule;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleListResponseDto {
  private Long no;
  private LocalDateTime workStartTime;
  private LocalDateTime workEndTime;
  private String color;
  private String state;
  private Long branchNo;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String branchName;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long employNo;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String employeeName;
  private String occupationName;

  public ScheduleListResponseDto(Long no, LocalDateTime workStartTime,
                                 LocalDateTime workEndTime, String color,
                                 String state, Long employNo, String employeeName,
                                 String occupationName, Long branchNo) {
    this.no = no;
    this.workStartTime = workStartTime;
    this.workEndTime = workEndTime;
    this.color = color;
    this.state = state;
    this.employNo = employNo;
    this.employeeName = employeeName;
    this.occupationName = occupationName;
    this.branchNo = branchNo;
  }

  public ScheduleListResponseDto(
          Long no, LocalDateTime workStartTime, LocalDateTime workEndTime,
          String color, String state, Long branchNo, String branchName,
          String occupationName) {
    this.no = no;
    this.workStartTime = workStartTime;
    this.workEndTime = workEndTime;
    this.color = color;
    this.state = state;
    this.branchNo = branchNo;
    this.branchName = branchName;
    this.occupationName = occupationName;
  }

  public ScheduleListResponseDto(Schedule entity) {
    this.no = entity.getNo();
    this.workStartTime = entity.getWorkStartTime();
    this.workEndTime = entity.getWorkEndTime();
    this.color = entity.getColor();
    this.state = entity.getState();
  }
}
