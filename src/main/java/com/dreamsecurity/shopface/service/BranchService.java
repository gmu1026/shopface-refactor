package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.dto.branch.BranchAddRequestDto;
import com.dreamsecurity.shopface.dto.branch.BranchEditRequestDto;
import com.dreamsecurity.shopface.dto.branch.BranchListResponseDto;
import com.dreamsecurity.shopface.dto.branch.BranchResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BranchService {
    Long addBranch(BranchAddRequestDto requestDto);
    List<BranchListResponseDto> getBranchList(String memberId);
    BranchResponseDto getBranch(long no);
    Long editBranch(long no, BranchEditRequestDto requestDto) throws IOException;
    void removeBranch(long no);
    Boolean confirmBranch(long no);
}
