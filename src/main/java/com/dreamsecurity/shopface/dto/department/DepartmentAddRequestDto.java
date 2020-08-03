package com.dreamsecurity.shopface.dto.department;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Department;
import lombok.Getter;

@Getter
public class DepartmentAddRequestDto {
  private String name;
  private Branch branch;

  public DepartmentAddRequestDto(Department entity) {
    this.name = entity.getName();
    this.branch = entity.getBranch();
  }

  public Department toEntity() {
    return Department.builder()
            .name(this.name)
            .branch(this.branch)
            .build();
  }
}
