package com.dreamsecurity.shopface.dto.schedule;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Member;
import com.dreamsecurity.shopface.domain.Schedule;
import lombok.Getter;

import java.util.Date;

@Getter
public class ScheduleListResponseDto {
  private long no;
  private Date workStartTime;
  private Date workEndTime;
  private String color;
  private String state;
  private Member member;
  private Branch branch;

  public ScheduleListResponseDto(Schedule entity) {
    this.no = entity.getNo();
    this.workStartTime = entity.getWorkStartTime();
    this.workEndTime = entity.getWorkEndTime();
    this.color = entity.getColor();
    this.state = entity.getState();
    this.member = entity.getMember();
    this.branch = entity.getBranch();
  }
}
