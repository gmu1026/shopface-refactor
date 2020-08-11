package com.dreamsecurity.shopface.dto.employ;

import com.dreamsecurity.shopface.domain.Department;
import com.dreamsecurity.shopface.domain.Employ;
import com.dreamsecurity.shopface.domain.Role;
import com.dreamsecurity.shopface.repository.EmployRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

@Getter
public class EmployResponseDto {
  private long no;
  private String name;
  private String state;
  private Long salary;
  private Long roleNo;
  private Long departmentNo;

  @JsonIgnore
  private Role role;

  @JsonIgnore
  private Department department;

  public EmployResponseDto(Employ entity) {
    this.no = entity.getNo();
    this.name = entity.getName();
    this.state = entity.getState();
    this.salary = entity.getSalary();
    this.role = entity.getRole();
    this.department = entity.getDepartment();
    this.roleNo = entity.getRole().getNo();
    this.departmentNo = entity.getDepartment().getNo();
  }
}
