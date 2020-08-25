package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.dto.availabletime.AvailableTimeAddRequestDto;
import com.dreamsecurity.shopface.dto.availabletime.AvailableTimeEditRequestDto;
import com.dreamsecurity.shopface.dto.availabletime.AvailableTimeListResponseDto;
import com.dreamsecurity.shopface.dto.availabletime.AvailableTimeResponseDto;

import java.util.List;

public interface AvailableTimeService {
    Long addAvailableTime(AvailableTimeAddRequestDto requestDto);
    List<AvailableTimeListResponseDto> getAvailableTimeList(String memberId);
    AvailableTimeResponseDto getAvailableTime(long no);
    Long editAvailableTime(long no, AvailableTimeEditRequestDto requestDto);
    void removeAvailableTime(long no);
}
