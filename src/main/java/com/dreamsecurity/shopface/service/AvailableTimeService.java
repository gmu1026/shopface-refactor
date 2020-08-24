package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.AvailableTime;
import com.dreamsecurity.shopface.dto.availabletime.*;

import java.util.List;

public interface AvailableTimeService {
    Long addAvailableTime(AvailableTimeAddRequestDto requestDto);
    List<AvailableTimeListResponseDto> getAvailableTimeListByBranchNo(Long branchNo);
    List<AvailableTimePersnalListResponseDto> getAvailableTimeListByMemberId(String memberId);
    AvailableTimeResponseDto getAvailableTime(long no);
    Long editAvailableTime(long no, AvailableTimeEditRequestDto requestDto);
    void removeAvailableTime(long no);
}
