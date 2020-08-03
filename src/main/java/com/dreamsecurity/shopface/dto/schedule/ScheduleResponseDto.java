package com.dreamsecurity.shopface.dto.schedule;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Member;
import com.dreamsecurity.shopface.domain.Schedule;
import lombok.Getter;

import java.util.Date;

@Getter
public class ScheduleResponseDto {
  private long no;
  private Date workStartTime;
  private Date workEndTime;
  private String color;
  private Member member;
  private Branch branch;
  private String state;

  public ScheduleResponseDto(Schedule entity) {
    this.no = entity.getNo();
    this.workStartTime = entity.getWorkStartTime();
    this.workEndTime = entity.getWorkEndTime();
    this.color = entity.getColor();
    this.member = entity.getMember();
    this.branch = entity.getBranch();
    this.state = entity.getState();
  }
}
