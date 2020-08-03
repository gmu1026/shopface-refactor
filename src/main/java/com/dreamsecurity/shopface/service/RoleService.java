package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.dto.role.RoleAddRequestDto;
import com.dreamsecurity.shopface.dto.role.RoleEditRequestDto;
import com.dreamsecurity.shopface.dto.role.RoleListResponseDto;

import java.util.List;

public interface RoleService {
    Long addRole(RoleAddRequestDto requestDto);
    List<RoleListResponseDto> getRoleList(long no);
    Long editRole(long no, RoleEditRequestDto requestDto);
    void removeRole(long no);
}
