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
  private String phone;
  private String email;
  private String bankName;
  private String accountNum;

  @JsonIgnore
  private Long roleNo;

  @JsonIgnore
  private Long departmentNo;

  @JsonIgnore
  private Role role;

  @JsonIgnore
  private Department department;

  public EmployResponseDto(long no, String name, String state, Long salary,
                           String phone, String email, String bankName,
                           String accountNum) {
    this.no = no;
    this.name = name;
    this.state = state;
    this.salary = salary;
    this.phone = phone;
    this.email = email;
    this.bankName = bankName;
    this.accountNum = accountNum;
  }

  public EmployResponseDto(Employ entity) {
    this.no = entity.getNo();
    this.name = entity.getName();
    this.state = entity.getState();
    this.salary = entity.getSalary();
//    this.role = entity.getRole();
//    this.department = entity.getDepartment();
//    this.roleNo = entity.getRole().getNo();
//    this.departmentNo = entity.getDepartment().getNo();
    if (entity.getMember() != null) {
      this.email = entity.getMember().getEmail();
      this.phone = entity.getMember().getPhone();
      this.bankName = entity.getMember().getBankName();
      this.accountNum = entity.getMember().getAccountNum();
    }
  }
}
