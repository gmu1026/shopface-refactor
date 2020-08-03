package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.AvailableTime;
import com.dreamsecurity.shopface.domain.Member;
import com.dreamsecurity.shopface.dto.availabletime.AvailableTimeAddRequestDto;
import com.dreamsecurity.shopface.dto.availabletime.AvailableTimeEditRequestDto;
import com.dreamsecurity.shopface.dto.availabletime.AvailableTimeListResponseDto;
import com.dreamsecurity.shopface.dto.availabletime.AvailableTimeResponseDto;
import com.dreamsecurity.shopface.repository.AvailableTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AvailableTimeServiceImpl implements AvailableTimeService {
    private final AvailableTimeRepository availableTimeRepository;

    @Override
    public Long addAvailableTime(AvailableTimeAddRequestDto requestDto) {
        return availableTimeRepository.save(requestDto.toEntity()).getNo();
    }

    @Override
    public List<AvailableTimeListResponseDto> getAvailableTimeList(String memberId) {
    return availableTimeRepository.findAllByMember(Member.builder().id(memberId).build())
            .stream().map(AvailableTimeListResponseDto::new)
            .collect(Collectors.toList());
    }

    @Override
    public AvailableTimeResponseDto getAvailableTime(long no) {
        AvailableTime availableTime = availableTimeRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 가용시간이 없습니다."));

        return new AvailableTimeResponseDto(availableTime);
    }

    @Override
    public Long editAvailableTime(long no, AvailableTimeEditRequestDto requestDto) {
        AvailableTime availableTime = availableTimeRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 가용시간이 없습니다."));

        availableTime.update(requestDto.getBranch(), requestDto.getStartTime(), requestDto.getEndTime());

        return no;
    }

    @Override
    public void removeAvailableTime(long no) {
        AvailableTime availableTime = availableTimeRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 가용시간이 없습니다."));

        availableTimeRepository.delete(availableTime);
    }
}
