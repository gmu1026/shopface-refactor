package com.dreamsecurity.shopface.dto.schedule;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Member;
import com.dreamsecurity.shopface.domain.Schedule;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
public class ScheduleListResponseDto {
  private Long no;
  private LocalDateTime workStartTime;
  private LocalDateTime workEndTime;
  private String color;
  private String state;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private Long employNo;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String employeeName;
  private String occupationName;

  public ScheduleListResponseDto(Long no, LocalDateTime workStartTime,
                                 LocalDateTime workEndTime, String color,
                                 String state, String occupationName) {
    this.no = no;
    this.workStartTime = workStartTime;
    this.workEndTime = workEndTime;
    this.color = color;
    this.state = state;
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
