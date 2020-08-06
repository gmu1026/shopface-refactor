package com.dreamsecurity.shopface.dto.alarm;

import com.dreamsecurity.shopface.domain.Alarm;
import com.dreamsecurity.shopface.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AlarmAddRequestDto {
  private String type;
  private String contents;
  private String memberId;
  private Member member;

  public AlarmAddRequestDto(String type, String contents, String memberId) {
    this.type = type;
    this.contents = contents;
    this.memberId = memberId;
  }

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

  public void setMember(Member member) {
    this.member = member;
  }
}
