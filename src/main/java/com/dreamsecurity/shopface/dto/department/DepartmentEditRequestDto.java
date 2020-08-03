package com.dreamsecurity.shopface.dto.department;

import com.dreamsecurity.shopface.domain.Department;
import lombok.Getter;

@Getter
public class DepartmentEditRequestDto {
    private String name;

    public DepartmentEditRequestDto(Department entity) {
        this.name = entity.getName();
    }
}
