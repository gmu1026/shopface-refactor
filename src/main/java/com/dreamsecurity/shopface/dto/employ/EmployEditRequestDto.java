package com.dreamsecurity.shopface.dto.employ;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployEditRequestDto {
  private long salary;
  private long roleNo;
  private long departmentNo;
  private String name;

  public void setRoleNo(long no) {
    this.roleNo = no;
  }

  public void setDepartmentNo(long no) {
    this.departmentNo = no;
  }
}
