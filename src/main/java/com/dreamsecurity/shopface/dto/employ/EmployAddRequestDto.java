package com.dreamsecurity.shopface.dto.employ;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Department;
import com.dreamsecurity.shopface.domain.Employ;
import com.dreamsecurity.shopface.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmployAddRequestDto {
  private String name;
  private Branch branch;
  private Role role;
  private Department department;

  public EmployAddRequestDto(Employ entity) {
    this.name = entity.getName();
    this.branch = entity.getBranch();
    this.role = entity.getRole();
    this.department = entity.getDepartment();
  }

  public Employ toEntity() {
    return Employ.builder()
            .name(this.name)
            .branch(this.branch)
            .role(this.role)
            .department(this.department)
            .build();
  }
}
