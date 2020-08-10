package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Schedule;
import com.dreamsecurity.shopface.dto.employ.EmployListResponseDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleAddRequestDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleEditRequestDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleListResponseDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleResponseDto;
import com.dreamsecurity.shopface.enums.ScheduleColor;
import com.dreamsecurity.shopface.enums.ScheduleState;
import com.dreamsecurity.shopface.repository.EmployRepository;
import com.dreamsecurity.shopface.repository.OccupationRepository;
import com.dreamsecurity.shopface.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ScheduleServiceImpl implements  ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final EmployRepository employRepository;
    private final OccupationRepository occupationRepository;

    private boolean checkSchedule(LocalDateTime oldStartDate,LocalDateTime oldEndDate, LocalDateTime newStartDate, LocalDateTime newEndDate) {
        boolean result = false;

        if ((newStartDate == oldStartDate)
                || (newEndDate == oldEndDate)) {
            result = false;
            new IllegalArgumentException("동시간대에 다른 스케줄이 존재합니다.");
        } else if (newEndDate.isBefore(oldStartDate)) {
            if (newStartDate.isBefore(oldEndDate)) {
                result = true;
            } else {
                result = false;
                new IllegalArgumentException("다른스케줄이 존재하여 등록할 수 없습니다.");
            }
        } else if (newEndDate.isAfter(oldStartDate)) {
            if (newStartDate.isAfter(oldEndDate)) {
                result = true;
            } else {
                result = false;
                new IllegalArgumentException("등록하려는 시간대에 다른스케줄이 존재하여 등록할 수 없습니다.");
            }
        } else {
            result = false;
            new IllegalArgumentException("등록하려는 시간대에 다른스케줄이 존재하여 등록할 수 없습니다.");
        }

        return result;
    }

    @Transactional
    @Override
    public Long addSchedule(ScheduleAddRequestDto requestDto) {
        if (requestDto.getWorkStartTime().isBefore(requestDto.getWorkEndTime())) {
            List<EmployListResponseDto> entity = employRepository.findByMemberIdAndBranchNo(requestDto.getMember().getId(), requestDto.getBranch().getNo());
            if (!entity.isEmpty()) {
                // 스케줄 중복 검사
                List<ScheduleListResponseDto> oldSchedules = scheduleRepository.findAllByTodayAndMemberIdAndBranchNo(requestDto.getWorkStartTime(),
                        requestDto.getMember().getId(),
                        requestDto.getBranch().getNo());
                for (ScheduleListResponseDto old : oldSchedules) {
                    try {
                        boolean isChecked = checkSchedule(old.getWorkStartTime(), old.getWorkEndTime(), requestDto.getWorkStartTime(), requestDto.getWorkEndTime());
                        if (!isChecked) {
                            new IllegalArgumentException("잘못된 스케줄입니다.");
                        }
                    } catch (IllegalArgumentException e) {
                        throw e;
                    }
                }
                // 업무 번호 검삭
                if (occupationRepository.findByNoAndBranchNo(requestDto.getOccupation().getNo(), requestDto.getBranch().getNo()) != null){
                    for (ScheduleColor color : ScheduleColor.values()) {
                        if (color.getColorCode().equals(requestDto.getColor())) {
                            return scheduleRepository.save(requestDto.toEntity()).getNo();
                        }
                    }

                    new IllegalArgumentException("등록할 수 없는 색상입니다.");
                }else {
                    new IllegalArgumentException("업무 명이 잘못되었습니다.");
                }
            }
            new IllegalArgumentException("등록할 수 없는 스케줄입니다.");
        }
        new IllegalArgumentException("종료시간이 시작시간보다 빠른 시각입니다.");
        return (long)0;
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

        if (requestDto.getWorkStartTime().isBefore(requestDto.getWorkEndTime())) {
            if (occupationRepository.findByNoAndBranchNo(requestDto.getOccupation().getNo(), requestDto.getOccupation().getBranch().getNo()) != null) {
                for (ScheduleColor color : ScheduleColor.values()) {
                    if (color.getColorCode().equals(requestDto.getColor())) {
                        for (ScheduleState state : ScheduleState.values()) {
                            if (state.getState().equals(requestDto.getState())) {
                                entity.update(requestDto.getMember(), requestDto.getWorkStartTime(),
                                        requestDto.getWorkEndTime(), requestDto.getOccupation(), requestDto.getColor());
                                return no;
                            }
                        } new IllegalArgumentException("잘못된 스케줄 상태입니다.");
                    }
                } new IllegalArgumentException("잘못된 색상입니다.");
            } new IllegalArgumentException("잘못된 업무입니다.");
        } new IllegalArgumentException("종료시간이 시작시간보다 빠른 시각입니다.");

        return no;
    }

    @Transactional
    @Override
    public void removeSchedule(long no) {
        Schedule entity = scheduleRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 없습니다."));
        if (!"W".equals(entity.getState())
                && !"L".equals(entity.getState())
                && !"A".equals(entity.getState())) {
            scheduleRepository.delete(entity);
        } else {
            new IllegalArgumentException("출근, 지각, 결근 상태인 스케줄은 삭제할 수 없습니다.");
        }
    }
}
