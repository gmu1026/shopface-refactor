package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.Alarm;
import com.dreamsecurity.shopface.dto.alarm.AlarmAddRequestDto;
import com.dreamsecurity.shopface.dto.alarm.AlarmListResponseDto;

import java.util.List;

public interface AlarmService {
    Long addAlarm(AlarmAddRequestDto requestDto);
    List<AlarmListResponseDto> getAlarmList(String memberId);
    Long readAlarm(long no);
    void removeAlarm(long no);
    List<AlarmListResponseDto> getAlarmLists(String memberId);
}
