package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Member;
import com.dreamsecurity.shopface.domain.Occupation;
import com.dreamsecurity.shopface.domain.Schedule;
import com.dreamsecurity.shopface.dto.employ.EmployListResponseDto;
import com.dreamsecurity.shopface.dto.occupation.OccupationResponseDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleAddRequestDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleEditRequestDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleListResponseDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleResponseDto;
import com.dreamsecurity.shopface.enums.ScheduleColor;
import com.dreamsecurity.shopface.enums.ScheduleState;
import com.dreamsecurity.shopface.repository.*;
import com.dreamsecurity.shopface.response.ApiException;
import com.dreamsecurity.shopface.response.ApiResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class ScheduleServiceImpl implements  ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final BranchRepository branchRepository;
    private final EmployRepository employRepository;
    private final OccupationRepository occupationRepository;

    @Transactional(readOnly = true)
    public boolean isOccupationNoChecked(Occupation occupation, Branch branch, String requestColor) {
        OccupationResponseDto existOccupation = occupationRepository.findByNoAndBranchNo(occupation.getNo(), branch.getNo());

        log.info(existOccupation.getName());
        if (existOccupation != null) {
            for (ScheduleColor color : ScheduleColor.values()) {
                log.info(color.getColorCode());
                if (color.getColorCode().equals(requestColor)) {
                    return true;
                }
            }
            new IllegalArgumentException("등록할 수 없는 색상입니다.");
            return false;
        }else {
            new IllegalArgumentException("업무 명이 잘못되었습니다.");
            return false;
        }
    }

    @Transactional(readOnly = true)
    public boolean checkEmploy (Member member, Branch branch) {
        boolean result = false;

        List<EmployListResponseDto> entity = employRepository.findByMemberIdAndBranchNo(
                member.getId(), branch.getNo());
        if(!entity.isEmpty()) {
            result = true;
        } else {
            result = false;
        }

        return result;
    }

    @Transactional
    @Override
    public Long addSchedule(ScheduleAddRequestDto requestDto) {
        Long result = 0L;

        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다"));

        Branch branch = branchRepository.findById(requestDto.getBranchNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 지점이 없습니다"));

        Occupation occupation = occupationRepository.findById(requestDto.getOccupationNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 업무가 없습니다"));

       if (scheduleRepository.existSchedule(requestDto.getWorkStartTime(),
               requestDto.getWorkEndTime(), requestDto.getMemberId())) {
           requestDto.setMember(member);
           requestDto.setBranch(branch);
           requestDto.setOccupation(occupation);
           result = scheduleRepository.save(requestDto.toEntity()).getNo();
       } else {
           throw new ApiException(ApiResponseCode.BAD_REQUEST, "요청한 시간에 스케줄이 이미 존재합니다");
       }

        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ScheduleListResponseDto> getScheduleList(long no) {
        return scheduleRepository.findAllBranchNo(no);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ScheduleListResponseDto> getScheduleList(String id) {
        return scheduleRepository.findAllByMemberId(id);
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
