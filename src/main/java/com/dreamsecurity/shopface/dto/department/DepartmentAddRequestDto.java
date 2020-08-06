package com.dreamsecurity.shopface.dto.department;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DepartmentAddRequestDto {
  private long branchNo;
  private String name;
  private Branch branch;

  public DepartmentAddRequestDto(String name, long branchNo) {
    this.name = name;
    this.branchNo = branchNo;
  }

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

  public void setBranch(Branch branch) {
    this.branch = branch;
  }
}
