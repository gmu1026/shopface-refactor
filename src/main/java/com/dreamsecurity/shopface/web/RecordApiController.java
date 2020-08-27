package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.record.RecordEditRequestDto;
import com.dreamsecurity.shopface.response.ApiResponseDto;
import com.dreamsecurity.shopface.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class RecordApiController {
  private final RecordService recordService;

  @GetMapping(value = "/member/{id}/record")
  public ApiResponseDto getRecordList(@PathVariable("id") String memberId) {
    return ApiResponseDto.createOK(recordService.getRecordList(memberId));
  }

  @GetMapping(value = "/branch/{no}/record")
  public ApiResponseDto getBranchRecordList(@PathVariable("no") Long no) {
    return ApiResponseDto.createOK(recordService.getBranchRecordList(no));
  }

  @PutMapping(value = "/record/{no}")
  public ApiResponseDto editRecord(
      @PathVariable("no") long no, @RequestBody RecordEditRequestDto requestDto) {
    return ApiResponseDto.createOK(recordService.editRecord(no, requestDto));
  }

  @DeleteMapping(value = "/record/{no}")
  public ApiResponseDto removeRecord(@PathVariable("no") long no) {
    recordService.removeRecord(no);

    return ApiResponseDto.createOK(true);
  }
}
