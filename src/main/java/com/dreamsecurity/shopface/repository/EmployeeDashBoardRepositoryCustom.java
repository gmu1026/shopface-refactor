package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.dto.dashboard.employeeDashBoard.EmployeeDashBoardResponseDto;
import com.dreamsecurity.shopface.dto.dashboard.employeeDashBoard.EmployeeDashBoardListResponseDto;

import java.util.List;

public interface EmployeeDashBoardRepositoryCustom {
    List<EmployeeDashBoardListResponseDto> getEmployeeDashBoardListScheduled(String memberId);
    List<EmployeeDashBoardListResponseDto> getEmployeeDashBoardListWorkDone(String memberId);
    EmployeeDashBoardResponseDto getEmployeeDashBoardCurrentWork(String memberId);
}
