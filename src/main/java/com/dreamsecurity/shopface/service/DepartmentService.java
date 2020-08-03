package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.dto.department.DepartmentAddRequestDto;
import com.dreamsecurity.shopface.dto.department.DepartmentEditRequestDto;
import com.dreamsecurity.shopface.dto.department.DepartmentListResponseDto;

import java.util.List;

public interface DepartmentService {
    Long addDepartment(DepartmentAddRequestDto requestDto);
    List<DepartmentListResponseDto> getDepartmentList(long no);
    Long editDepartment(long no, DepartmentEditRequestDto requestDto);
    void removeDepartment(long no);
}
