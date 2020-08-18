package com.dreamsecurity.shopface.dto.schedule;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Member;
import com.dreamsecurity.shopface.domain.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@AllArgsConstructor
public class ScheduleListResponseDto {
  private long no;
  private LocalDateTime workStartTime;
  private LocalDateTime workEndTime;
  private String color;
  private String state;
  private Long employNo;
//  private String memberId;
//  private Long branchNo;
  private String employeeName;
  private String occupationName;

  public ScheduleListResponseDto(Schedule entity) {
    this.no = entity.getNo();
    this.workStartTime = entity.getWorkStartTime();
    this.workEndTime = entity.getWorkEndTime();
    this.color = entity.getColor();
    this.state = entity.getState();
//    this.memberId = entity.getMember().getId();
  }
}
