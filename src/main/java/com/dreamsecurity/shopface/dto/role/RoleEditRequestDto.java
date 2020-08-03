package com.dreamsecurity.shopface.dto.role;

import com.dreamsecurity.shopface.domain.Role;
import lombok.Getter;

@Getter
public class RoleEditRequestDto {
    private String name;

    public RoleEditRequestDto(Role entity) {
        this.name = entity.getName();
    }
}
