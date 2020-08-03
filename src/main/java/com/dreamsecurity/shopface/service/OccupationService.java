package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.dto.occupation.OccupationAddRequestDto;
import com.dreamsecurity.shopface.dto.occupation.OccupationEditRequestDto;
import com.dreamsecurity.shopface.dto.occupation.OccupationListResponseDto;

import java.util.List;

public interface OccupationService {
    Long addOccupation(OccupationAddRequestDto requestDto);
    List<OccupationListResponseDto> getOccupationList(long no);
    Long editOccupation(long no, OccupationEditRequestDto requestDto);
    void removeOccupation(long no);
}
