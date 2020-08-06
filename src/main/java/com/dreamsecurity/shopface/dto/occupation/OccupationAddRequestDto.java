package com.dreamsecurity.shopface.dto.occupation;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Occupation;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OccupationAddRequestDto {
  private String name;
  private Long branchNo;
  private Branch branch;

  public OccupationAddRequestDto(String name, long branchNo) {
    this.name = name;
    this.branchNo = branchNo;
  }

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

  public void setBranch(Branch branch) {
    this.branch = branch;
  }
}
