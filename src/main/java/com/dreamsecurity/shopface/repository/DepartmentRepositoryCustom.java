package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.dto.department.DepartmentListResponseDto;

import java.util.List;

public interface DepartmentRepositoryCustom {
    List<DepartmentListResponseDto> findAllByBranchNo(long no);
}
