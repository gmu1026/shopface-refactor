package com.dreamsecurity.shopface.dto.alarm;

import com.dreamsecurity.shopface.domain.Alarm;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AlarmListResponseDto {
  private long no;
  private String contents;
  private String type;
  private LocalDateTime registerDate;
  private String checkState;

  public AlarmListResponseDto(Alarm entity) {
    this.no = entity.getNo();
    this.contents = entity.getContents();
    this.type = entity.getType();
    this.registerDate = entity.getRegisterDate();
    this.checkState = entity.getCheckState();
  }
}
