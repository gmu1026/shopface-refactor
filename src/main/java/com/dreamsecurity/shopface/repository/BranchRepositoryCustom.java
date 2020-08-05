package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.dto.branch.BranchListResponseDto;

import java.util.List;

public interface BranchRepositoryCustom {
    List<BranchListResponseDto> findAllByMemberId(String memberId);
}
