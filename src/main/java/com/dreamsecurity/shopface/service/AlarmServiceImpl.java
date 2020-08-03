package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.Alarm;
import com.dreamsecurity.shopface.dto.alarm.AlarmAddRequestDto;
import com.dreamsecurity.shopface.dto.alarm.AlarmEditRequestDto;
import com.dreamsecurity.shopface.dto.alarm.AlarmListResponseDto;
import com.dreamsecurity.shopface.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AlarmServiceImpl implements AlarmService {
    private final AlarmRepository alarmRepository;

    @Transactional
    @Override
    public Long addAlarm(AlarmAddRequestDto requestDto) {
        return alarmRepository.save(requestDto.toEntity()).getNo();
    }

    @Transactional(readOnly = true)
    @Override
    public List<AlarmListResponseDto> getAlarmList(String memberId) {
        return alarmRepository.findAllByMemberId(memberId).stream()
                .map(AlarmListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Long editAlarm(long no, AlarmEditRequestDto requestDto) {
        Alarm alarm = alarmRepository.findByNo(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 알림이 존재하지 않습니다."));
        alarm.update(requestDto.getCheckState());

        return no;
    }

    @Override
    public void removeAlarm(long no) {
        Alarm alarm = alarmRepository.findByNo(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 알림이 존재하지 않습니다."));

        alarmRepository.delete(alarm);
    }
}
