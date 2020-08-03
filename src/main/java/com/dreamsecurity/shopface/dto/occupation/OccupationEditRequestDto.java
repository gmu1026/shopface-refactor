package com.dreamsecurity.shopface.dto.occupation;

import com.dreamsecurity.shopface.domain.Occupation;
import lombok.Getter;

@Getter
public class OccupationEditRequestDto {
  private String name;

  public OccupationEditRequestDto(Occupation entity) {
    this.name = entity.getName();
  }
}
