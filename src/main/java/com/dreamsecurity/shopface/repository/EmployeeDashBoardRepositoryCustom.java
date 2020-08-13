package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.dto.dashboard.employeeDashBoard.EmployeeDashBoardListRequestDto;
import com.dreamsecurity.shopface.dto.dashboard.employeeDashBoard.EmployeeDashBoardListResponseDto;

import java.util.List;

public interface EmployeeDashBoardRepositoryCustom {
    List<EmployeeDashBoardListResponseDto> getEmployeeDashBoardList(EmployeeDashBoardListRequestDto requestDto);
}
