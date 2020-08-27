package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.dashboard.businessmanDashBoard.BusinessmanDashBoardListRequestDto;
import com.dreamsecurity.shopface.enums.ScheduleState;
import com.dreamsecurity.shopface.response.ApiException;
import com.dreamsecurity.shopface.response.ApiResponseCode;
import com.dreamsecurity.shopface.response.ApiResponseDto;
import com.dreamsecurity.shopface.service.BusinessmanDashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BusinessmanDashBoardApiController {
    private final BusinessmanDashBoardService businessmanDashBoardService;

    // 사업자용 대시보드 목록 조회
    @GetMapping(value = "/businessman/{id}/branch/{no}/{status}")
    public ApiResponseDto getBusinessmanDashBoardList(@PathVariable("id") String id, @PathVariable("no") Long branchNo, @PathVariable("status") String status) {
        BusinessmanDashBoardListRequestDto requestDto = BusinessmanDashBoardListRequestDto.builder()
                .businessmanId(id)
                .branchNo(branchNo)
                .scheduleStatus(status).build();

        if (ScheduleState.REGISTER.getState().equals(status) || ScheduleState.LATE.getState().equals(status)) {
            return ApiResponseDto.createOK(businessmanDashBoardService.getBusinessmanDashBoardListScheduled(requestDto));
        } else if (ScheduleState.GO_WORKING.getState().equals(status)) {
            return ApiResponseDto.createOK(businessmanDashBoardService.getBusinessmanDashBoardListWorking(requestDto));
        } else if (ScheduleState.ABSENTEEISM.getState().equals(status) || ScheduleState.COMPLETE.getState().equals(status)){
            return ApiResponseDto.createOK(businessmanDashBoardService.getBusinessmanDashBoardListWorkDone(requestDto));
        } else {
            throw new ApiException(ApiResponseCode.BAD_REQUEST);
        }
    }
}
