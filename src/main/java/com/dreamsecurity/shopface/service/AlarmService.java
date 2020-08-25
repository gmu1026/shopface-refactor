package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.dto.alarm.AlarmListResponseDto;

import java.util.List;

public interface AlarmService {
    Long readAlarm(long no);
    void removeAlarm(long no);
    List<AlarmListResponseDto> getAlarmLists(String memberId);
}
