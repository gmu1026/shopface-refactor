package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.dto.dashboard.employeeDashBoard.EmployeeDashBoardListRequestDto;
import com.dreamsecurity.shopface.dto.dashboard.employeeDashBoard.EmployeeDashBoardListResponseDto;

import java.util.List;

public interface EmployeeDashBoardService {
    List<EmployeeDashBoardListResponseDto> getEmployeeDashBoardList(EmployeeDashBoardListRequestDto requestDto);
}
