package com.dreamsecurity.shopface.dto.role;

import com.dreamsecurity.shopface.domain.Role;
import lombok.Getter;

@Getter
public class RoleListResponseDto {
  private long no;
  private String name;

  public RoleListResponseDto(Role entity) {
    this.no = entity.getNo();
    this.name = entity.getName();
  }
}
