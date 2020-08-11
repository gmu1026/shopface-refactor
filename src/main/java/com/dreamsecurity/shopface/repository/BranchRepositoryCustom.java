package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Employ;
import com.dreamsecurity.shopface.dto.branch.BranchListResponseDto;

import java.util.List;
import java.util.Optional;

public interface BranchRepositoryCustom {
    List<BranchListResponseDto> findAllByMemberId(String memberId);
}