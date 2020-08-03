package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.dto.member.MemberAddRequestDto;
import com.dreamsecurity.shopface.dto.member.MemberEditRequestDto;
import com.dreamsecurity.shopface.dto.member.MemberListResponseDto;
import com.dreamsecurity.shopface.dto.member.MemberResponseDto;

import java.util.List;

public interface MemberService {
    String addMember(MemberAddRequestDto requestDto);
    List<MemberListResponseDto> getMemberList();
    MemberResponseDto getMember(String id);
    String editMember(String id, MemberEditRequestDto requestDto);
    void removeMember(String id);
}
