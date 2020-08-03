package com.dreamsecurity.shopface.dto.schedule;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Member;
import com.dreamsecurity.shopface.domain.Occupation;
import com.dreamsecurity.shopface.domain.Schedule;
import lombok.Getter;

import java.util.Date;

@Getter
public class ScheduleAddRequestDto {
  private Date workStartTime;
  private Date workEndTime;
  private String color;
  private Member member;
  private Branch branch;
  private Occupation occupation;

  public ScheduleAddRequestDto(Schedule entity) {
    this.workStartTime = entity.getWorkStartTime();
    this.workEndTime = entity.getWorkEndTime();
    this.color = entity.getColor();
    this.member = entity.getMember();
    this.branch = entity.getBranch();
    this.occupation = entity.getOccupation();
  }

  public Schedule toEntity() {
    return Schedule.builder()
            .member(this.member)
            .branch(this.branch)
            .workStartTime(this.workStartTime)
            .workEndTime(this.workEndTime)
            .occupation(this.occupation)
            .color(this.color)
            .build();
  }
}
