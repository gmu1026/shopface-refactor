package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.availabletime.AvailableTimeAddRequestDto;
import com.dreamsecurity.shopface.dto.availabletime.AvailableTimeEditRequestDto;
import com.dreamsecurity.shopface.response.ApiResponseDto;
import com.dreamsecurity.shopface.service.AvailableTimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RequiredArgsConstructor
@RestController
public class AvailableTimeApiController {
    private final AvailableTimeService availableTimeService;

    @GetMapping(value = "/availabletime/{no}")
    public ApiResponseDto getAvailableTime(@PathVariable("no") long no) {
        return ApiResponseDto.createOK(availableTimeService.getAvailableTime(no));
    }

    @GetMapping(value = "/branch/{branchNo}/availabletime")
    public ApiResponseDto getAvailableTimeListByBranchNo(@PathVariable("branchNo") Long branchNo) {
        return ApiResponseDto.createOK(availableTimeService.getAvailableTimeListByBranchNo(branchNo));
    }

    @GetMapping(value = "/member/{id}/availabletime")
    public ApiResponseDto getMyAvailableTimeList(@PathVariable("id") String memberId) {
        return ApiResponseDto.createOK(availableTimeService.getAvailableTimeListByMemberId(memberId));
    }

    @PostMapping(value = "/availabletime")
    public ApiResponseDto addAvailableTime(@RequestBody AvailableTimeAddRequestDto requestDto) {
        return ApiResponseDto.createOK(availableTimeService.addAvailableTime(requestDto));
    }

    @PutMapping(value = "/availabletime/{no}")
    public ApiResponseDto editAvailableTime(@PathVariable("no") long no,
                                            @RequestBody AvailableTimeEditRequestDto requestDto) {
        return ApiResponseDto.createOK(availableTimeService.editAvailableTime(no, requestDto));
    }

    @DeleteMapping(value = "/availabletime/{no}")
    public ApiResponseDto removeAvailableTime(@PathVariable("no") long no) {
        availableTimeService.removeAvailableTime(no);

        return ApiResponseDto.createOK(true);
    }
}
