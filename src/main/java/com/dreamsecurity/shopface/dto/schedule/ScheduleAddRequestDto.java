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
public class ScheduleAddRequestDto {
  private LocalDateTime workStartTime;
  private LocalDateTime workEndTime;
  private String color;
  private Long employNo;
  private Long occupationNo;
  private String state = "R";

  @JsonIgnore
  private Member member;

  @JsonIgnore
  private Branch branch;

  @JsonIgnore
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
            .state(this.state)
            .build();
  }

  public void setEmployNo(Long employNo) {
    this.employNo = employNo;
  }

  public void setOccupationNo(Long occupationNo) {
    this.occupationNo = occupationNo;
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
