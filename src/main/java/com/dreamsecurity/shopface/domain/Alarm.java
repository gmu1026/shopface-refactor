package com.dreamsecurity.shopface.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Alarm extends BaseTimeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long no;

  @ManyToOne @JoinColumn private Member member;

  @Column(nullable = false)
  private String type;

  @Column(nullable = false)
  private String contents;

  @Column(nullable = false, length = 1)
  private String checkState;

  @Builder
  public Alarm(String type, String contents, String checkState, Member member) {
    this.type = type;
    this.contents = contents;
    this.checkState = checkState;
    this.member = member;
  }

  public void readAlarm() {
    this.checkState = "Y";
  }
}
