package com.dreamsecurity.shopface.dto.department;

import com.dreamsecurity.shopface.domain.Department;
import lombok.Getter;

@Getter
public class DepartmentListResponseDto {
  private long no;
  private String name;

  public DepartmentListResponseDto(long no, String name) {
    this.no = no;
    this.name = name;
  }

  public DepartmentListResponseDto(Department entity) {
    this.no = entity.getNo();
    this.name = entity.getName();
  }
}
