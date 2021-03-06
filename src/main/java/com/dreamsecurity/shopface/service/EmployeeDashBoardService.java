package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.dto.dashboard.employeeDashBoard.EmployeeDashBoardResponseDto;
import com.dreamsecurity.shopface.dto.dashboard.employeeDashBoard.EmployeeDashBoardListResponseDto;

import java.util.List;

public interface EmployeeDashBoardService {
  List<EmployeeDashBoardListResponseDto> getEmployeeDashBoardListScheduled(String memberId);
  List<EmployeeDashBoardListResponseDto> getEmployeeDashBoardListWorkDone(String memberId);
  EmployeeDashBoardResponseDto getEmployeeDashBoardCurrentSchedule(String memberId);
}