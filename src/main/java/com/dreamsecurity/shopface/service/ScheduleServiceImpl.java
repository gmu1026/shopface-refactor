package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Schedule;
import com.dreamsecurity.shopface.dto.schedule.ScheduleAddRequestDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleEditRequestDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleListResponseDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleResponseDto;
import com.dreamsecurity.shopface.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ScheduleServiceImpl implements  ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Transactional
    @Override
    public Long addSchedule(ScheduleAddRequestDto requestDto) {
        return scheduleRepository.save(requestDto.toEntity()).getNo();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ScheduleListResponseDto> getScheduleList(long no) {
    return scheduleRepository.findAllBranchNo(no).stream()
        .map(ScheduleListResponseDto::new)
        .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public ScheduleResponseDto getSchedule(long no) {
        Schedule entity = scheduleRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 없습니다."));

        return new ScheduleResponseDto(entity);
    }

    @Transactional
    @Override
    public Long editSchedule(long no, ScheduleEditRequestDto requestDto) {
        Schedule entity = scheduleRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 없습니다."));

        entity.update(requestDto.getMember(), requestDto.getWorkStartTime(),
                requestDto.getWorkEndTime(), requestDto.getOccupation(), requestDto.getColor());

        return no;
    }

    @Transactional
    @Override
    public void removeSchedule(long no) {
        Schedule entity = scheduleRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 없습니다."));

        scheduleRepository.delete(entity);
    }
}
