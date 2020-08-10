package com.dreamsecurity.shopface.dto.employ;

import com.dreamsecurity.shopface.domain.Department;
import com.dreamsecurity.shopface.domain.Employ;
import com.dreamsecurity.shopface.domain.Role;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@Getter
public class EmployListResponseDto {
  private long no;
  private String name;
  private String state;
  private Long salary;
  private LocalDateTime employDate;
  private String roleName;
  private String departmentName;

  @JsonIgnore
  private Role role;

  @JsonIgnore
  private Department department;

  public EmployListResponseDto(Employ employ, Role role, Department department) {
    if (role != null) {
      this.roleName = role.getName();
    }

    if (department != null) {
      this.departmentName = department.getName();
    }

    this.no = employ.getNo();
    this.name = employ.getName();
    this.state = employ.getState();
    this.salary = employ.getSalary();
    this.employDate = employ.getEmployDate();
  }
}
