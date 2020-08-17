package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.dto.dashboard.businessmanDashBoard.BusinessmanDashBoardListResponseDto;

import java.util.List;

public interface BusinessmanDashBoardService {
    List<BusinessmanDashBoardListResponseDto> getBusinessmanDashBoardList();
}
