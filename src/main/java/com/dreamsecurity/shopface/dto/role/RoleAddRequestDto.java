package com.dreamsecurity.shopface.dto.role;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Role;
import lombok.Getter;

@Getter
public class RoleAddRequestDto {
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
}
