package com.dreamsecurity.shopface.dto.alarm;

import com.dreamsecurity.shopface.domain.Alarm;
import com.dreamsecurity.shopface.domain.Member;
import lombok.Getter;

import java.util.Date;

@Getter
public class AlarmListResponseDto {
  private long no;
  private String contents;
  private String type;
  private Date registerDate;
  private String checkState;
  private Member member;

  public AlarmListResponseDto(Alarm entity) {
    this.no = entity.getNo();
    this.contents = entity.getContents();
    this.type = entity.getType();
    this.registerDate = entity.getRegisterDate();
    this.checkState = entity.getCheckState();
    this.member = entity.getMember();
  }
}
