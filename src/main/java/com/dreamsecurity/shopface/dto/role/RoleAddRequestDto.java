package com.dreamsecurity.shopface.dto.role;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(value = { "branch" })
public class RoleAddRequestDto {
  private long branchNo;
  private String name;
  private Branch branch;

  public RoleAddRequestDto(Role entity) {
    this.name = entity.getName();
    this.branch = entity.getBranch();
  }

  public Role toEntity() {
    return Role.builder()
            .name(this.name)
            .branch(this.branch)
            .build();
  }

  public RoleAddRequestDto(String name, long branchNo) {
    this.name = name;
  this.branchNo = branchNo;
  }

  public void setBranch(Branch branch) {
    this.branch = branch;
  }
}
