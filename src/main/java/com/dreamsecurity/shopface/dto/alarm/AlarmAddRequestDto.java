package com.dreamsecurity.shopface.dto.alarm;

import com.dreamsecurity.shopface.domain.Alarm;
import com.dreamsecurity.shopface.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class AlarmAddRequestDto {
  private Member member;
  private String type;
  private String contents;

  public AlarmAddRequestDto(Alarm entity) {
    this.member = entity.getMember();
    this.type = entity.getType();
    this.contents = entity.getContents();
  }

  public Alarm toEntity() {
    return Alarm.builder()
            .type(this.type)
            .contents(this.contents)
            .member(this.member)
            .build();
  }
}
