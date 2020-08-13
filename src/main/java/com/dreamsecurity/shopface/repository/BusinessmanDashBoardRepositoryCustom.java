package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.dto.dashboard.businessmanDashBoard.BusinessmanDashBoardListRequestDto;
import com.dreamsecurity.shopface.dto.dashboard.businessmanDashBoard.BusinessmanDashBoardListResponseDto;

import java.util.List;

public interface BusinessmanDashBoardRepositoryCustom {
    List<BusinessmanDashBoardListResponseDto> getBusinessmanDashBoardList(BusinessmanDashBoardListRequestDto requestDto);
}
