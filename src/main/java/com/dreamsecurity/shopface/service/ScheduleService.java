package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.dto.schedule.ScheduleAddRequestDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleEditRequestDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleListResponseDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
    Long addSchedule(ScheduleAddRequestDto requestDto);
    List<ScheduleListResponseDto> getScheduleList(long no);
    ScheduleResponseDto getSchedule(long no);
    Long editSchedule(long no, ScheduleEditRequestDto requestDto);
    void removeSchedule(long no);
}
