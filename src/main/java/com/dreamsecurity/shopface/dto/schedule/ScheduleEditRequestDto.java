package com.dreamsecurity.shopface.dto.schedule;

import com.dreamsecurity.shopface.domain.Member;
import com.dreamsecurity.shopface.domain.Occupation;
import com.dreamsecurity.shopface.domain.Schedule;
import lombok.Getter;

import java.util.Date;

@Getter
public class ScheduleEditRequestDto {
  private Date workStartTime;
  private Date workEndTime;
  private String color;
  private Member member;
  private Occupation occupation;

  public ScheduleEditRequestDto(Schedule entity) {
    this.workStartTime = entity.getWorkStartTime();
    this.workEndTime = entity.getWorkEndTime();
    this.color = entity.getColor();
    this.member = entity.getMember();
    this.occupation = entity.getOccupation();
  }
}
