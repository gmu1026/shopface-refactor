package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.branch.BranchAddRequestDto;
import com.dreamsecurity.shopface.dto.branch.BranchEditRequestDto;
import com.dreamsecurity.shopface.response.ApiResponseDto;
import com.dreamsecurity.shopface.service.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@RestController
public class BranchApiController {
  private final BranchService branchService;

  @GetMapping(value = "/branch")
  public ApiResponseDto getBranchList() {
    return ApiResponseDto.createOK(branchService.getBranchList());
  }

  @GetMapping(value = "/member/{id}/branch")
  public ApiResponseDto getBranchList(@PathVariable("id") String memberId) {
    return ApiResponseDto.createOK(branchService.getBranchList(memberId));
  }

  @GetMapping(value = "/branch/{no}")
  public ApiResponseDto getBranch(@PathVariable("no") long no) {
    return ApiResponseDto.createOK(branchService.getBranch(no));
  }

  @PostMapping(value = "/branch")
  public ApiResponseDto addBranch(@RequestBody BranchAddRequestDto requestDto) {
    return ApiResponseDto.createOK(branchService.addBranch(requestDto));
  }

  @PutMapping(value = "/branch/{no}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ApiResponseDto editBranch(
          @PathVariable("no") long no, BranchEditRequestDto requestDto) throws IOException {
    return ApiResponseDto.createOK(branchService.editBranch(no, requestDto));
  }

  @DeleteMapping(value = "/branch/{no}")
  public ApiResponseDto removeBranch(@PathVariable("no") long no) {
    branchService.removeBranch(no);

    return ApiResponseDto.createOK(true);
  }

  @PatchMapping(value = "/branch/{no}/confirm")
  public ApiResponseDto confirmBranch(@PathVariable("no") long no) {
    return ApiResponseDto.createOK(branchService.confirmBranch(no));
  }

  @PatchMapping(value = "/branch/{no}/reject")
  public ApiResponseDto rejectBranch(@PathVariable("no") long no) {
    return ApiResponseDto.createOK(branchService.rejectBranch(no));
  }
}
