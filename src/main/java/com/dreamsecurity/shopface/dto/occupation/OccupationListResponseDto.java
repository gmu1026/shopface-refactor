package com.dreamsecurity.shopface.dto.occupation;

import com.dreamsecurity.shopface.domain.Occupation;
import lombok.Getter;

@Getter
public class OccupationListResponseDto {
  private long no;
  private String name;

  public OccupationListResponseDto(Occupation entity) {
    this.no = entity.getNo();
    this.name = entity.getName();
  }
}
