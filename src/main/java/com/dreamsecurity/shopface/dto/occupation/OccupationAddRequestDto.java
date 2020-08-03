package com.dreamsecurity.shopface.dto.occupation;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Occupation;
import lombok.Getter;

@Getter
public class OccupationAddRequestDto {
  private String name;
  private Branch branch;

  public OccupationAddRequestDto(Occupation entity) {
    this.name = entity.getName();
    this.branch = entity.getBranch();
  }

  public Occupation toEntity() {
    return Occupation.builder()
            .name(this.name)
            .branch(this.branch)
            .build();
  }
}
