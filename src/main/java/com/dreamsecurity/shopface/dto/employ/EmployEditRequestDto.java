package com.dreamsecurity.shopface.dto.employ;

import com.dreamsecurity.shopface.domain.Department;
import com.dreamsecurity.shopface.domain.Employ;
import com.dreamsecurity.shopface.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EmployEditRequestDto {
  private long salary;
  private Role role;
  private Department department;

  public EmployEditRequestDto(Employ entity) {
    this.salary = entity.getSalary();
    this.role = entity.getRole();
    this.department = entity.getDepartment();
  }
}
