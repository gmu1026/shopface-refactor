package com.dreamsecurity.shopface.dto.occupation;

import com.dreamsecurity.shopface.domain.Occupation;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OccupationEditRequestDto {
  private String name;

  public OccupationEditRequestDto(String name) {
    this.name = name;
  }

  public OccupationEditRequestDto(Occupation entity) {
    this.name = entity.getName();
  }
}
