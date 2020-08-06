package com.dreamsecurity.shopface.dto.department;

import com.dreamsecurity.shopface.domain.Department;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DepartmentEditRequestDto {
    private String name;

    public DepartmentEditRequestDto(String name) {
        this.name = name;
    }

    public DepartmentEditRequestDto(Department entity) {
        this.name = entity.getName();
    }
}
