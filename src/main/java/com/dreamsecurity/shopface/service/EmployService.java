package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.Employ;
import com.dreamsecurity.shopface.dto.employ.*;
import org.springframework.mail.SimpleMailMessage;

import java.util.List;

public interface EmployService {
    Long addEmploy(EmployAddRequestDto requestDto);
    List<EmployListResponseDto> getEmployList(long no);
    EmployResponseDto getEmploy(long no);
    Long editEmploy(long no, EmployEditRequestDto requestDto);
    void removeEmploy(long no);
    boolean sendInviteMail(EmployAddRequestDto requestDto, String certCode);
    String createCode();
    SimpleMailMessage createInviteMessage(String email, String name, String branchName, String certCode);
    boolean joiningEmployee(EmployAcceptRequestDto requestDto);
    boolean checkCode(String requestCertCode);
    boolean disableEmployee(long no);
}
