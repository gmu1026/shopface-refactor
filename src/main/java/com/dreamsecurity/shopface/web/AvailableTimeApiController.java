package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.availabletime.AvailableTimeAddRequestDto;
import com.dreamsecurity.shopface.dto.availabletime.AvailableTimeEditRequestDto;
import com.dreamsecurity.shopface.response.ApiResponseDto;
import com.dreamsecurity.shopface.service.AvailableTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AvailableTimeApiController {
    private final AvailableTimeService availableTimeService;

    @GetMapping(value = "/availabletime/{no}")
    public ApiResponseDto getAvailableTime(@PathVariable("no") long no) {
        return ApiResponseDto.createOK(availableTimeService.getAvailableTime(no));
    }

    @GetMapping(value = "/member/{id}/availabletime")
    public ApiResponseDto getAvailableTimeList(@PathVariable("id") String memberId) {
        return ApiResponseDto.createOK(availableTimeService.getAvailableTimeList(memberId));
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
