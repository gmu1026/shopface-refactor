package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Schedule;
import com.dreamsecurity.shopface.dto.schedule.ScheduleListResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ScheduleRepositoryCustom {
    List<ScheduleListResponseDto> findAllByMemberIdAndBranchNo(String id, long no);
    List<Schedule> findAllByToday();
    List<Schedule> findAllByTodayAndMemberId(String memberId);
    List<ScheduleListResponseDto> findAllByTodayAndMemberIdAndBranchNo(LocalDateTime workStartTime, String memberId, long branchNo);
    List<ScheduleListResponseDto> findAllBranchNo(long no);
    List<Schedule> findAllByBranchNoAndOccupationNo(long branchNo, long occupationNo);
    Boolean existSchedule(LocalDateTime startTime, LocalDateTime endTime, String memberId);
}
