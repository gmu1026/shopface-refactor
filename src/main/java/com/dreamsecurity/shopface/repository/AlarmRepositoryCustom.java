package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.dto.alarm.AlarmListResponseDto;

import java.util.List;

public interface AlarmRepositoryCustom {
    List<AlarmListResponseDto> findAllByMemberIdAsc(String memberId);
}
