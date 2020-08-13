package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.*;
import com.dreamsecurity.shopface.dto.employ.*;
import com.dreamsecurity.shopface.repository.*;
import com.dreamsecurity.shopface.response.ApiException;
import com.dreamsecurity.shopface.response.ApiResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmployServiceImpl implements EmployService {
    private final MemberRepository memberRepository;
    private final EmployRepository employRepository;
    private final BranchRepository branchRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final JavaMailSender mailSender;

    @Transactional
    @Override
    public Long addEmploy(EmployAddRequestDto requestDto) {
        Branch branch = branchRepository.findById(requestDto.getBranchNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 지점이 없습니다"));

        if (requestDto.getRoleNo() > 0) {
            Role role = roleRepository.findById(requestDto.getRoleNo())
                    .orElseThrow(() -> new IllegalArgumentException("해당 역할이 없습니다"));

            requestDto.setRole(role);
        }

        if (requestDto.getDepartmentNo() > 0) {
            Department department = departmentRepository.findById(requestDto.getDepartmentNo())
                    .orElseThrow(() -> new IllegalArgumentException("해당 부서가 없습니다"));

            requestDto.setDepartment(department);
        }
        requestDto.setBranch(branch);

        String certCode = createCode();
        Employ employ = requestDto.toEntity();
        employ.inviteMember(certCode);
        sendInviteMail(requestDto, certCode);

        return employRepository.save(employ).getNo();
    }

    @Transactional(readOnly = true)
    @Override
    public List<EmployListResponseDto> getEmployList(long no) {
        return employRepository.findAllByBranchNo(no);
    }

    @Transactional(readOnly = true)
    @Override
    public EmployResponseDto getEmploy(long no) {
        Employ entity = employRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 고용 정보가 없습니다."));

        return new EmployResponseDto(entity);
    }

    @Transactional
    @Override
    public Long editEmploy(long no, EmployEditRequestDto requestDto) {
        Employ entity = employRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 고용 정보가 없습니다."));
        Role role = roleRepository.findById(requestDto.getRoleNo())
                .orElseThrow(() -> new IllegalIdentifierException("해당 역할이 없습니다"));
        Department department = departmentRepository.findById(requestDto.getDepartmentNo())
                .orElseThrow(() -> new IllegalIdentifierException("해당 부서가 없습니다"));

        entity.update(requestDto.getSalary(), role, department);

        return no;
    }

    @Transactional
    @Override
    public void removeEmploy(long no) {
        Employ entity = employRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 고용 정보가 없습니다."));

        employRepository.delete(entity);
    }

    @Override
    public boolean sendInviteMail(EmployAddRequestDto requestDto, String certCode) {
        boolean isSuccess = false;
        try{
            mailSender.send(createInviteMessage(requestDto.getEmail(),
                    requestDto.getName(), requestDto.getBranch().getName(), certCode));

            isSuccess = true;
        } catch (MailException e) {
            new ApiException(ApiResponseCode.SERVER_ERROR, "다시 시도해주세요");
        }
        return isSuccess;
    }

    @Override
    public SimpleMailMessage createInviteMessage(String email, String name, String branchName, String certCode) {
        SimpleMailMessage message = new SimpleMailMessage();

        StringBuilder content = new StringBuilder();
        content.append(branchName + "으로부터 근무자 합류 초대를 하였습니다.\n");
        content.append("https://cproduction.net/authcode\n");
        content.append("초대코드를 입력해주세요.\n");
        content.append(certCode);

        message.setTo(email);
        message.setText(content.toString());
        message.setSubject(branchName + "지점 초대 메일");

        return message;
    }

    @Override
    public String createCode() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        String code = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return code;
    }

    @Transactional
    @Override
    public boolean joiningEmployee(EmployAcceptRequestDto requestDto) {
        Member member = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다"));

        Employ employ = employRepository.findByCertCode(requestDto.getCertCode());

        boolean isSuccess = false;
        if (checkCode(requestDto.getCertCode())) {
            employ.joinMember(member);
            isSuccess = true;
        } else {
            new ApiException(ApiResponseCode.BAD_REQUEST, "코드가 일치하지 않습니다");
        }

        return isSuccess;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean checkCode(String requestCertCode) {
        boolean isChecked = false;

        // TODO exist로 바꿀 것
        if (employRepository.findByCertCode(requestCertCode) != null) {
            isChecked = true;
        }

        return isChecked;
    }
}
