package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.*;
import com.dreamsecurity.shopface.dto.employ.*;
import com.dreamsecurity.shopface.repository.*;
import com.dreamsecurity.shopface.response.ApiException;
import com.dreamsecurity.shopface.response.ApiResponseCode;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class EmployServiceImpl implements EmployService {
    private final MemberRepository memberRepository;
    private final EmployRepository employRepository;
    private final BranchRepository branchRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;
    private final AlarmRepository alarmRepository;
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
        Employ employ = employRepository.findMemberAndEmployById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다"));

        return new EmployResponseDto(employ);
    }

    @Transactional
    @Override
    public Long editEmploy(long no, EmployEditRequestDto requestDto) {
        Employ entity = employRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 고용 정보가 없습니다."));

        Role role = null;
        if (requestDto.getRoleNo() > 0) {
            role = roleRepository.findById(requestDto.getRoleNo())
                    .orElseThrow(() -> new IllegalIdentifierException("해당 역할이 없습니다"));
        }

        Department department = null;
        if (requestDto.getDepartmentNo() > 0) {
            department = departmentRepository.findById(requestDto.getDepartmentNo())
                    .orElseThrow(() -> new IllegalIdentifierException("해당 부서가 없습니다"));
        }
        entity.update(requestDto.getSalary(), role, department, requestDto.getName());

        return no;
    }

    @Transactional
    @Override
    public void removeEmploy(long no) {
        Employ entity = employRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 고용 정보가 없습니다."));

        employRepository.delete(entity);
    }

    private void sendInviteMail(EmployAddRequestDto requestDto, String certCode) {
        try{
            mailSender.send(createInviteMessage(requestDto.getEmail(),
                    requestDto.getBranch().getName(), certCode));
        } catch (MailException e) {
            e.printStackTrace();
        }
    }

    private SimpleMailMessage createInviteMessage(String email, String branchName, String certCode) {
        SimpleMailMessage message = new SimpleMailMessage();

        StringBuilder content = new StringBuilder();
        content.append(branchName).append("으로부터 근무자 합류 초대를 하였습니다.\n");
        content.append("https://cproduction.net/certcode\n");
        content.append("초대코드를 입력해주세요.\n");
        content.append(certCode);

        message.setTo(email);
        message.setText(content.toString());
        message.setSubject(branchName + "지점 초대 메일");

        return message;
    }

    private String createCode() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
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

            Alarm alarm = Alarm.builder()
                    .member(employ.getBranch().getMember())
                    .contents(employ.getBranch().getName() + " 지점에 근무자 " + member.getName() + "이(가) 합류하였습니다.")
                    .type("JOIN_EMPLOYEE")
                    .build();
            alarmRepository.save(alarm);

            isSuccess = true;
        } else {
            throw new ApiException(ApiResponseCode.BAD_REQUEST, "코드가 일치하지 않습니다");
        }

        return isSuccess;
    }

    @Transactional(readOnly = true)
    @Override
    public boolean checkCode(String requestCertCode) {
        boolean isChecked = false;

        if (employRepository.existCertCode(requestCertCode)) {
            isChecked = true;
        }

        return isChecked;
    }

    @Transactional
    @Override
    public boolean disableEmployee(long no) {
        Employ employ = employRepository.findById(no)
                .orElseThrow(() -> new ApiException(
                        ApiResponseCode.NOT_FOUND, "해당 고용 정보가 없습니다"));

        employ.disabledEmployee();

        return true;
    }

    @Transactional
    @Override
    public boolean reInviteEmployee(long no) {
        Employ employ = employRepository.findById(no)
                .orElseThrow(() -> new ApiException(ApiResponseCode.NOT_FOUND,
                        "해당 고용 정보가 없습니다"));

        String certCode = createCode();
        sendInviteMail(new EmployAddRequestDto(employ), certCode);
        employ.inviteMember(certCode);

        return true;
    }
}
