package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.dto.dashboard.employeeDashBoard.EmployeeDashBoardListRequestDto;
import com.dreamsecurity.shopface.dto.dashboard.employeeDashBoard.EmployeeDashBoardListResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeDashBoardServiceImpl implements EmployeeDashBoardService{
    @Override
    public List<EmployeeDashBoardListResponseDto> getEmployeeDashBoardList(EmployeeDashBoardListRequestDto requestDto) {
        return null;
    }
}
