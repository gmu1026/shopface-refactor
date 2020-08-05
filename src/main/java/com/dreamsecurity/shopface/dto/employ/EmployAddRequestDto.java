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
  private long branchNo;
  private long roleNo;
  private long departmentNo;

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

  public void setBranch(Branch branch) {
    this.branch = branch;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public void setBranchNo(long no) {
    this.branchNo = no;
  }

  public void setRoleNo(long no) {
    this.roleNo = no;
  }

  public void setDepartmentNo(long no) {
    this.departmentNo = no;
  }
}
