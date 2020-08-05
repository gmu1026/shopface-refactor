package com.dreamsecurity.shopface.dto.role;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
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

  public void setBranch(Branch branch) {
    this.branch = branch;
  }
}
