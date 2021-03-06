package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.AvailableTime;
import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Member;
import com.dreamsecurity.shopface.dto.availabletime.*;
import com.dreamsecurity.shopface.repository.AvailableTimeRepository;
import com.dreamsecurity.shopface.repository.BranchRepository;
import com.dreamsecurity.shopface.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@RequiredArgsConstructor
@Service
public class AvailableTimeServiceImpl implements AvailableTimeService {
    private final AvailableTimeRepository availableTimeRepository;
    private final MemberRepository memberRepository;
    private final BranchRepository branchRepository;

    @Transactional
    @Override
    public Long addAvailableTime(AvailableTimeAddRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        Branch branch = branchRepository.findById(requestDto.getBranchNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 지점이 없습니다."));

        requestDto.setBranch(branch);
        requestDto.setMember(member);

        LocalDateTime start = LocalDateTime.of(requestDto.getStartTime().getYear(),
                requestDto.getStartTime().getMonth(),
                requestDto.getStartTime().getDayOfMonth(),
                0, 0, 0);
        LocalDateTime end = LocalDateTime.of(requestDto.getEndTime().getYear(),
                requestDto.getEndTime().getMonth(),
                requestDto.getEndTime().getDayOfMonth(),
                0,0,0);

        if (start.isEqual(end)) {
            end = LocalDateTime.of(end.getYear(), end.getMonth(), end.getDayOfMonth() + 1,0,0,0);
        }

        LocalDateTime newStart = requestDto.getStartTime();
        LocalDateTime newEnd = requestDto.getEndTime();

        List<AvailableTime> responseDtos = availableTimeRepository.findAllByMemberIdAndBetweenStartDateAndEndDate(start, end, requestDto.getMember().getId());

        for (AvailableTime responseDto : responseDtos) {
            LocalDateTime oldStart = responseDto.getStartTime();
            LocalDateTime oldEnd = responseDto.getEndTime();

            if (!((oldStart.isEqual(newStart)) && (oldEnd.isEqual(newEnd)))) {
                if (!(((newStart.isAfter(oldStart) || newStart.isEqual(oldStart))
                            && (newStart.isBefore(oldEnd) || newStart.isEqual(oldEnd)))
                        && (newEnd.isAfter(oldStart) || newEnd.isEqual(oldStart))
                            && (newEnd.isBefore(oldEnd) || newEnd.isEqual(oldEnd)))) {
                    if (oldStart.isAfter(newStart) && oldEnd.isBefore(newEnd)) {
                        availableTimeRepository.delete(responseDto);
                    } else if (((newStart.isAfter(oldStart) || newStart.isEqual(oldStart))
                                && (newStart.isBefore(oldEnd) || newStart.isEqual(oldEnd)))
                            || (newEnd.isAfter(oldStart) || newEnd.isEqual(oldStart))
                                && (newEnd.isBefore(oldEnd) || newEnd.isEqual(oldEnd))) {
                        newStart = (oldStart.isAfter(newStart) || oldStart.isEqual(newStart)) ? newStart : oldStart;
                        newEnd = (oldEnd.isBefore(newEnd) || oldEnd.isEqual(newEnd)) ? newEnd : oldEnd;
                        availableTimeRepository.delete(responseDto);
                    } else if (((oldStart.isAfter(newStart)) && (oldStart.isAfter(newEnd)))
                            || ((oldEnd.isBefore(newStart)) && (oldEnd.isBefore(newEnd)))) {
                        continue;
                    } else { new IllegalArgumentException("중복된 가용시간이 존재합니다."); return 0L;}
                } else { new IllegalArgumentException("중복된 가용시간이 존재합니다."); return 0L; }
            } else { new IllegalArgumentException("중복된 가용시간이 존재합니다."); return 0L;}
        }
        requestDto.setStartTime(newStart);
        requestDto.setEndTime(newEnd);

        return availableTimeRepository.save(requestDto.toEntity()).getNo();
    }

    @Override
    public List<AvailableTimeListResponseDto> getAvailableTimeListByBranchNo(Long branchNo) {
        return availableTimeRepository.findAllByBranchNo(branchNo);
    }

    @Override
    public List<AvailableTimePersnalListResponseDto> getAvailableTimeListByMemberId(String memberId){
        return availableTimeRepository.findAllByMemberId(memberId);
    }

    @Override
    public AvailableTimeResponseDto getAvailableTime(long no) {
        AvailableTime availableTime = availableTimeRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 가용시간이 없습니다."));

        return new AvailableTimeResponseDto(availableTime);
    }

    @Transactional
    @Override
    public Long editAvailableTime(long no, AvailableTimeEditRequestDto requestDto) {
        AvailableTime availableTime = availableTimeRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 가용시간이 없습니다."));
        List<AvailableTime> responseDtos = availableTimeRepository.findAllByMemberIdAndStartTimeAndEndTime(no, requestDto.getStartTime(), requestDto.getEndTime(),requestDto.getMemberId());

        for (AvailableTime responseDto : responseDtos) {
            requestDto.setStartTime(requestDto.getStartTime().isBefore(responseDto.getStartTime()) ? requestDto.getStartTime() : responseDto.getStartTime());
            requestDto.setEndTime(requestDto.getEndTime().isAfter(responseDto.getEndTime()) ? requestDto.getEndTime() : responseDto.getEndTime());
            availableTimeRepository.delete(responseDto);
        }
        availableTime.update(requestDto.getStartTime(), requestDto.getEndTime());

        return no;
    }

    @Override
    public void removeAvailableTime(long no) {
        AvailableTime availableTime = availableTimeRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 가용시간이 없습니다."));

        availableTimeRepository.delete(availableTime);
    }
}
