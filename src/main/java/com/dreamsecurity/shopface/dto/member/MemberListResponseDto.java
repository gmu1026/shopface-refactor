package com.dreamsecurity.shopface.dto.member;

import com.dreamsecurity.shopface.domain.Member;
import lombok.Getter;

@Getter
public class MemberListResponseDto {
  private String id;
  private String name;
  private String type;
  private String state;

  public MemberListResponseDto(Member entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.type = entity.getType();
    this.state = entity.getState();
  }
}
