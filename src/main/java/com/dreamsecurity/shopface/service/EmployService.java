package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.dto.employ.*;

import java.util.List;

public interface EmployService {
    Long addEmploy(EmployAddRequestDto requestDto);
    List<EmployListResponseDto> getEmployList(long no);
    EmployResponseDto getEmploy(long no);
    Long editEmploy(long no, EmployEditRequestDto requestDto);
    void removeEmploy(long no);
    boolean joiningEmployee(EmployAcceptRequestDto requestDto);
    boolean checkCode(String requestCertCode);
    boolean disableEmployee(long no);
    boolean reInviteEmployee(long no);
}
