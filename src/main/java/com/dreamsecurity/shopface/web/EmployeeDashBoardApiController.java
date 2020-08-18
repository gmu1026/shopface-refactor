package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.dashboard.employeeDashBoard.EmployeeDashBoardListRequestDto;
import com.dreamsecurity.shopface.enums.ScheduleState;
import com.dreamsecurity.shopface.response.ApiResponseDto;
import com.dreamsecurity.shopface.service.EmployeeDashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class EmployeeDashBoardApiController {
    private final EmployeeDashBoardService employeeDashBoardService;

    // 근무자용 대시보드 목록 조회
    @GetMapping(value = "/employee/{id}/{status}")
    public ApiResponseDto getEmployeeDashBoardList(@PathVariable("id") String id, @PathVariable("status") String status) {
        if (ScheduleState.REGISTER.getState().equals(status) || ScheduleState.LATE.getState().equals(status)) {
            return ApiResponseDto.createOK(employeeDashBoardService.getEmployeeDashBoardListScheduled(id));
        } else {
            return ApiResponseDto.createOK(employeeDashBoardService.getEmployeeDashBoardListWorkDone(id));
        }
    }
}
