package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.dto.dashboard.businessmanDashBoard.BusinessmanDashBoardListRequestDto;
import com.dreamsecurity.shopface.dto.dashboard.businessmanDashBoard.BusinessmanDashBoardListResponseDto;

import java.util.List;

public interface BusinessmanDashBoardService {
    List<BusinessmanDashBoardListResponseDto> getBusinessmanDashBoardListScheduled(BusinessmanDashBoardListRequestDto requestDto);
    List<BusinessmanDashBoardListResponseDto> getBusinessmanDashBoardListWorking(BusinessmanDashBoardListRequestDto requestDto);
    List<BusinessmanDashBoardListResponseDto> getBusinessmanDashBoardListWorkDone(BusinessmanDashBoardListRequestDto requestDto);
}
