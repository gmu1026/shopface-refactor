package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.dto.role.RoleListResponseDto;

import java.util.List;

public interface RoleRepositoryCustom {
    List<RoleListResponseDto> findAllByBranchNo(long no);
}
