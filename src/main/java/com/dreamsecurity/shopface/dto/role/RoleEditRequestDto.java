package com.dreamsecurity.shopface.dto.role;

import com.dreamsecurity.shopface.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoleEditRequestDto {
    private String name;

    public RoleEditRequestDto(String name) {
        this.name = name;
    }

    public RoleEditRequestDto(Role entity) {
        this.name = entity.getName();
    }
}
