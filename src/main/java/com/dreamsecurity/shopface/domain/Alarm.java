package com.dreamsecurity.shopface.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class Alarm {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long no;

  @ManyToOne @JoinColumn private Member member;

  private String type;

  private String contents;

  @Temporal(TemporalType.TIMESTAMP)
  private Date registerDate;

  @Column(nullable = false, length = 1)
  private String checkState;

  @Builder
  public Alarm(String type, String contents, Date registerDate, String checkState, Member member) {
    this.type = type;
    this.contents = contents;
    this.registerDate = registerDate;
    this.checkState = checkState;
    this.member = member;
  }

  public void update(String checkState) {
    this.checkState = checkState;
  }
}
