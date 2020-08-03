package com.dreamsecurity.shopface.dto.employ;

import com.dreamsecurity.shopface.domain.Department;
import com.dreamsecurity.shopface.domain.Employ;
import com.dreamsecurity.shopface.domain.Role;
import lombok.Getter;

import java.util.Date;

@Getter
public class EmployListResponse {
  private long no;
  private String name;
  private String state;
  private long salary;
  private Date employDate;
  private Role role;
  private Department department;

  public EmployListResponse(Employ entity) {
    this.no = entity.getNo();
    this.name = entity.getName();
    this.state = entity.getState();
    this.employDate = entity.getEmployDate();
    this.salary = entity.getSalary();
    this.role = entity.getRole();
    this.department = entity.getDepartment();
  }
}
