package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.dto.occupation.OccupationListResponseDto;
import com.dreamsecurity.shopface.dto.occupation.OccupationResponseDto;

import java.util.List;

public interface OccupationRepositoryCustom {
    List<OccupationListResponseDto> findAllByBranchNo(long no);
    OccupationResponseDto findByNoAndBranchNo(long no, long branchNo);
}
