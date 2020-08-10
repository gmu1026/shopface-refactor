package com.dreamsecurity.shopface.dto.schedule;

import com.dreamsecurity.shopface.domain.Member;
import com.dreamsecurity.shopface.domain.Occupation;
import com.dreamsecurity.shopface.domain.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class ScheduleEditRequestDto {
  private LocalDateTime workStartTime;
  private LocalDateTime workEndTime;
  private String color;
  private Member member;
  private Occupation occupation;
  private String state;

  public ScheduleEditRequestDto(Schedule entity) {
    this.workStartTime = entity.getWorkStartTime();
    this.workEndTime = entity.getWorkEndTime();
    this.color = entity.getColor();
    this.member = entity.getMember();
    this.occupation = entity.getOccupation();
    this.state = entity.getState();
  }
}
