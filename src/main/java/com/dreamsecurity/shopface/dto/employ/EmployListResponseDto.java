package com.dreamsecurity.shopface.dto.employ;

import com.dreamsecurity.shopface.domain.Department;
import com.dreamsecurity.shopface.domain.Employ;
import com.dreamsecurity.shopface.domain.Role;
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
  private long salary;
  private LocalDateTime employDate;
  private String roleName;
  private String departmentName;
}
