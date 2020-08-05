package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.dto.employ.EmployListResponseDto;

import java.util.List;

public interface EmployRepositoryCustom {
    List<EmployListResponseDto> findAllByBranchNo(long no);
}
