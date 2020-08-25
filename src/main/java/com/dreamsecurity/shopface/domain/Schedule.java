package com.dreamsecurity.shopface.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Schedule {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long no;

  @ManyToOne @JoinColumn private Member member;

  @ManyToOne @JoinColumn private Branch branch;

  @Column
  private LocalDateTime workStartTime;

  @Column
  private LocalDateTime workEndTime;

  @OneToOne
  @JoinColumn
  private Occupation occupation;

  @Column(nullable = false, length = 7)
  private String color;

  @Column(nullable = false, length = 1)
  private String state;

  @Builder
  public Schedule(
      Member member,
      Branch branch,
      LocalDateTime workStartTime,
      LocalDateTime workEndTime,
      Occupation occupation,
      String color,
      String state) {
    this.member = member;
    this.branch = branch;
    this.workStartTime = workStartTime;
    this.workEndTime = workEndTime;
    this.occupation = occupation;
    this.color = color;
    this.state = state;
  }

  public void update(
      Member member,
      LocalDateTime workStartTime,
      LocalDateTime workEndTime,
      Occupation occupation,
      String color) {
    if (member != null) {
      this.member = member;
    }

    if (workStartTime != null) {
      this.workStartTime = workStartTime;
    }

    if (workEndTime != null) {
      this.workEndTime = workEndTime;
    }

    if (occupation != null) {
      this.occupation = occupation;
    }

    if (color != null) {
      this.color = color;
    }
  }

  public void workingSchedule() {
    this.state = "W";
  }

  public void quittingSchedule() {
    this.state = "C";
  }

  public void absenteeism() {
    this.state = "A";
  }
}
