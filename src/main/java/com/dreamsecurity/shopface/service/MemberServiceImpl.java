package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.Employ;
import com.dreamsecurity.shopface.domain.Member;
import com.dreamsecurity.shopface.dto.member.MemberAddRequestDto;
import com.dreamsecurity.shopface.dto.member.MemberEditRequestDto;
import com.dreamsecurity.shopface.dto.member.MemberListResponseDto;
import com.dreamsecurity.shopface.dto.member.MemberResponseDto;
import com.dreamsecurity.shopface.repository.EmployRepository;
import com.dreamsecurity.shopface.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final EmployRepository employRepository;

    @Transactional
    @Override
    public String addMember(MemberAddRequestDto requestDto) {
        // TODO 비밀번호 암호화
        if (employRepository.findByCertCode(requestDto.getCertCode()) != null) {
            requestDto.setType("E");
            Member employee = requestDto.toEntity();
            return memberRepository.save(employee).getId();
        }
        else {
            requestDto.setType("B");
            return memberRepository.save(requestDto.toEntity()).getId();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<MemberListResponseDto> getMemberList() {
        // TODO JWT Scope 설정을 통한 권한 확인
        
        // TODO Util Class 만들 것 - ASC, DESC
        Sort sort = Sort.by(Sort.Direction.ASC, "id");

        return memberRepository.findAll(sort).stream()
                .map(MemberListResponseDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public MemberResponseDto getMember(String id) {
        Member entity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        return new MemberResponseDto(entity);
    }

    @Transactional
    @Override
    public String editMember(String id, MemberEditRequestDto requestDto) {
        Member entity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        entity.update(requestDto.getPassword(), requestDto.getAddress(),
                requestDto.getDetailAddress(), requestDto.getZipCode(),
                requestDto.getEmail(), requestDto.getBankName(), requestDto.getAccountNum());

        return id;
    }

    @Transactional
    @Override
    public void removeMember(String id) {
        Member entity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        memberRepository.delete(entity);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean checkDuplicateId(String id) {
        return memberRepository.findById(id).isPresent();
    }

    @Transactional
    @Override
    public String confirmMember(String id) {
        Member entity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 존재하지 않습니다."));

        entity.confirm();

        return entity.getId();
    }

    @Transactional
    @Override
    public void joinEmployee(String certCode, String memberId) {
        Employ employ = employRepository.findByCertCode(certCode);
        Member employee = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다"));

        employ.joinMember(employee);
    }
}
