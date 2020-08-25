package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.Alarm;
import com.dreamsecurity.shopface.dto.alarm.AlarmListResponseDto;
import com.dreamsecurity.shopface.repository.AlarmRepository;
import com.dreamsecurity.shopface.repository.MemberRepository;
import com.dreamsecurity.shopface.response.ApiException;
import com.dreamsecurity.shopface.response.ApiResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AlarmServiceImpl implements AlarmService {
    private final AlarmRepository alarmRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    @Override
    public List<AlarmListResponseDto> getAlarmLists(String memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(() -> new ApiException(ApiResponseCode.NOT_FOUND, "해당 회원이 없습니다"))
                .getAlarms()
                .stream()
                .map(AlarmListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Long readAlarm(long no) {
        Alarm alarm = alarmRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 알림이 존재하지 않습니다."));

        alarm.readAlarm();

        return no;
    }

    @Transactional
    @Override
    public void removeAlarm(long no) {
        Alarm alarm = alarmRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 알림이 존재하지 않습니다."));

        alarmRepository.delete(alarm);
    }
}
