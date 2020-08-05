package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.dto.employ.EmployAddRequestDto;
import com.dreamsecurity.shopface.dto.employ.EmployEditRequestDto;
import com.dreamsecurity.shopface.dto.employ.EmployListResponseDto;
import com.dreamsecurity.shopface.dto.employ.EmployResponseDto;

import java.util.List;

public interface EmployService {
    Long addEmploy(EmployAddRequestDto requestDto);
    List<EmployListResponseDto> getEmployList(long no);
    EmployResponseDto getEmploy(long no);
    Long editEmploy(long no, EmployEditRequestDto requestDto);
    void removeEmploy(long no);
}
