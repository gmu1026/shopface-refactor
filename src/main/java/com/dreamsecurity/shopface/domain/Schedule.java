package com.dreamsecurity.shopface.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class Schedule {
  @PrePersist
  public void setDefaultState() {
    this.state = this.state == null ? "R" : this.state;
    // R = Ready
  }

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
    this.member = member;
    this.workStartTime = workStartTime;
    this.workEndTime = workEndTime;
    this.occupation = occupation;
    this.color = color;
  }

  public void startSchedule() {
    this.state = "W";
    // W = Working
  }

  public void endSchedule() {
    this.state = "C";
    // C = Complete
  }
}
