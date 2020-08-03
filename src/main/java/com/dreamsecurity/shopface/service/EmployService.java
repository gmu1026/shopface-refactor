package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.dto.employ.EmployAddRequestDto;
import com.dreamsecurity.shopface.dto.employ.EmployEditRequestDto;
import com.dreamsecurity.shopface.dto.employ.EmployListResponse;
import com.dreamsecurity.shopface.dto.employ.EmployResponseDto;

import java.util.List;

public interface EmployService {
    Long addEmploy(EmployAddRequestDto requestDto);
    List<EmployListResponse> getEmployList(long no);
    EmployResponseDto getEmploy(long no);
    Long editEmploy(long no, EmployEditRequestDto requestDto);
    void removeAlarm(long no);
}
