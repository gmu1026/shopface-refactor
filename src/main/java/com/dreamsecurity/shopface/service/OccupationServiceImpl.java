package com.dreamsecurity.shopface.service;


import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Occupation;
import com.dreamsecurity.shopface.dto.occupation.OccupationAddRequestDto;
import com.dreamsecurity.shopface.dto.occupation.OccupationEditRequestDto;
import com.dreamsecurity.shopface.dto.occupation.OccupationListResponseDto;
import com.dreamsecurity.shopface.repository.BranchRepository;
import com.dreamsecurity.shopface.repository.OccupationRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OccupationServiceImpl implements OccupationService{
    private final OccupationRepository occupationRepository;
    private final BranchRepository branchRepository;

    @Transactional
    @Override
    public Long addOccupation(OccupationAddRequestDto requestDto) {
        Branch branch = branchRepository.findById(requestDto.getBranchNo())
                .orElseThrow(() -> new IllegalIdentifierException("해당 지점이 없습니다"));

        requestDto.setBranch(branch);

        return occupationRepository.save(requestDto.toEntity()).getNo();
    }

    @Transactional(readOnly = true)
    @Override
    public List<OccupationListResponseDto> getOccupationList(long no) {
        return occupationRepository.findAllByBranchNo(no);
    }

    @Transactional
    @Override
    public Long editOccupation(long no, OccupationEditRequestDto requestDto) {
        Occupation entity = occupationRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 업무가 존재하지 않습니다."));

        entity.update(requestDto.getName());

        return no;
    }

    @Transactional
    @Override
    public void removeOccupation(long no) {
        Occupation entity = occupationRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 업무가 존재하지 않습니다."));

        occupationRepository.delete(entity);
    }
}
