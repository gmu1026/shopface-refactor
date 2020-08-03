package com.dreamsecurity.shopface.dto.alarm;

import com.dreamsecurity.shopface.domain.Alarm;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AlarmEditRequestDto {
  private String checkState;

  public AlarmEditRequestDto(Alarm entity) {
    this.checkState = entity.getCheckState();
  }
}
