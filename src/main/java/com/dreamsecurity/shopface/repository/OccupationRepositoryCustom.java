package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.dto.occupation.OccupationListResponseDto;

import java.util.List;

public interface OccupationRepositoryCustom {
    List<OccupationListResponseDto> findAllByBranchNo(long no);
}
