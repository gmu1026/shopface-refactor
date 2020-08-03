package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.dto.branch.BranchAddRequestDto;
import com.dreamsecurity.shopface.dto.branch.BranchEditRequestDto;
import com.dreamsecurity.shopface.dto.branch.BranchListResponseDto;
import com.dreamsecurity.shopface.dto.branch.BranchResponseDto;

import java.util.List;

public interface BranchService {
    Long addBranch(BranchAddRequestDto requestDto);
    List<BranchListResponseDto> getBranchList(String memberId);
    BranchResponseDto getBranch(long no);
    Long editBranch(long no, BranchEditRequestDto requestDto);
    void removeBranch(long no);
}
