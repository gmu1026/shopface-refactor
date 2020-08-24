package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.AvailableTime;
import com.dreamsecurity.shopface.dto.availabletime.AvailableTimeListResponseDto;
import com.dreamsecurity.shopface.dto.availabletime.AvailableTimePersnalListResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface AvailableTimeRepositoryCustom {
    List<AvailableTimePersnalListResponseDto> findAllByMemberIdAndBetweenStartDateAndEndDate(LocalDateTime start, LocalDateTime end, String memberId);
    List<AvailableTimePersnalListResponseDto> findAllByMemberId(String memberId);
    List<AvailableTime> findAllByMemberIdAndStartTimeAndEndTime(Long no, LocalDateTime start, LocalDateTime end, String memberId);
    List<AvailableTimeListResponseDto> findAllByBranchNo(Long branchNo);
}
