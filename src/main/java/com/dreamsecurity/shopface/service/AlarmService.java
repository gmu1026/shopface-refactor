package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.dto.alarm.AlarmAddRequestDto;
import com.dreamsecurity.shopface.dto.alarm.AlarmEditRequestDto;
import com.dreamsecurity.shopface.dto.alarm.AlarmListResponseDto;

import java.util.List;

public interface AlarmService {
    Long addAlarm(AlarmAddRequestDto requestDto);
    List<AlarmListResponseDto> getAlarmList(String memberId);
    Long editAlarm(long no, AlarmEditRequestDto requestDto);
    void removeAlarm(long no);
}
