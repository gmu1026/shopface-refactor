package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.dto.branch.BranchAddRequestDto;
import com.dreamsecurity.shopface.dto.branch.BranchEditRequestDto;
import com.dreamsecurity.shopface.dto.branch.BranchListResponseDto;
import com.dreamsecurity.shopface.dto.branch.BranchResponseDto;
import com.dreamsecurity.shopface.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;

    @Transactional
    @Override
    public Long addBranch(BranchAddRequestDto requestDto) {
        return branchRepository.save(requestDto.toEntity()).getNo();
    }

    @Transactional(readOnly = true)
    @Override
    public List<BranchListResponseDto> getBranchList(String memberId) {
        return branchRepository.findAll().stream()
                .map(BranchListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public BranchResponseDto getBranch(long no) {
        Branch entity = branchRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 지점이 없습니다."));
        return new BranchResponseDto(entity);
    }

    @Transactional
    @Override
    public Long editBranch(long no, BranchEditRequestDto requestDto) {
        Branch entity = branchRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 지점이 없습니다."));

        entity.update(requestDto.getName(), requestDto.getAddress(),
                requestDto.getDetailAddress(), requestDto.getZipCode(),
                requestDto.getBusinessLicensePath());
        return no;
    }

    @Transactional
    @Override
    public void removeBranch(long no) {
        Branch entity = branchRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 지점이 없습니다."));

        branchRepository.delete(entity);
    }
}
