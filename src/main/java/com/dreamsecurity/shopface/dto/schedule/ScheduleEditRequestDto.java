package com.dreamsecurity.shopface.dto.schedule;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Member;
import com.dreamsecurity.shopface.domain.Occupation;
import com.dreamsecurity.shopface.domain.Schedule;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ScheduleEditRequestDto {
  private LocalDateTime workStartTime;
  private LocalDateTime workEndTime;
  private String color;
  private long employNo;
  private long occupationNo;

  @JsonIgnore
  private Member member;

  @JsonIgnore
  private Branch branch;

  @JsonIgnore
  private Occupation occupation;

  public ScheduleEditRequestDto(Schedule entity) {
    this.workStartTime = entity.getWorkStartTime();
    this.workEndTime = entity.getWorkEndTime();
    this.color = entity.getColor();
  }

  public void setMember(Member member) {
    this.member = member;
  }

  public void setBranch(Branch branch) {
    this.branch = branch;
  }

  public void setOccupation(Occupation occupation) {
    this.occupation = occupation;
  }
}
