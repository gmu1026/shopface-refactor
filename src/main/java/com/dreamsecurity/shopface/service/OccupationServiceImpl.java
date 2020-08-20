package com.dreamsecurity.shopface.service;


import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Occupation;
import com.dreamsecurity.shopface.domain.Schedule;
import com.dreamsecurity.shopface.dto.occupation.OccupationAddRequestDto;
import com.dreamsecurity.shopface.dto.occupation.OccupationEditRequestDto;
import com.dreamsecurity.shopface.dto.occupation.OccupationListResponseDto;
import com.dreamsecurity.shopface.repository.BranchRepository;
import com.dreamsecurity.shopface.repository.OccupationRepository;
import com.dreamsecurity.shopface.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OccupationServiceImpl implements OccupationService{
    private final OccupationRepository occupationRepository;
    private final BranchRepository branchRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional
    @Override
    public Long addOccupation(OccupationAddRequestDto requestDto) {
        Branch branch = branchRepository.findById(requestDto.getBranchNo())
                .orElseThrow(() -> new IllegalArgumentException("사업장이 존재하지 않습니다."));

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

        List<Schedule> results = scheduleRepository.findAllByBranchNoAndOccupationNo(entity.getBranch().getNo(), no);
        if (results.size() > 0) {
            for (Schedule responses : results ) {
                responses.update(responses.getMember(), responses.getWorkStartTime(), responses.getWorkEndTime(), null, responses.getColor());
            }
        }
        occupationRepository.delete(entity);
    }
}
