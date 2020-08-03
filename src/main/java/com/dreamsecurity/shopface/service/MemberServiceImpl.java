package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.Member;
import com.dreamsecurity.shopface.dto.member.MemberAddRequestDto;
import com.dreamsecurity.shopface.dto.member.MemberEditRequestDto;
import com.dreamsecurity.shopface.dto.member.MemberListResponseDto;
import com.dreamsecurity.shopface.dto.member.MemberResponseDto;
import com.dreamsecurity.shopface.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public String addMember(MemberAddRequestDto requestDto) {
        return memberRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    @Override
    public List<MemberListResponseDto> getMemberList() {
        return memberRepository.findAll().stream()
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
}
