package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.schedule.ScheduleAddRequestDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleEditRequestDto;
import com.dreamsecurity.shopface.response.ApiResponseDto;
import com.dreamsecurity.shopface.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ScheduleApiController {
  private final ScheduleService scheduleService;

  @GetMapping(value = "/branch/{no}/schedule")
  public ApiResponseDto getScheduleList(@PathVariable("no") long no) {
    return ApiResponseDto.createOK(scheduleService.getScheduleList(no));
  }

  @GetMapping(value = "/member/{id}/schedule")
  private ApiResponseDto getScheduleList(@PathVariable("id") String id) {
    return ApiResponseDto.createOK(scheduleService.getScheduleList(id));
  }

  @GetMapping(value = "/schedule/{no}")
  public ApiResponseDto getSchedule(@PathVariable("no") long no) {
    return ApiResponseDto.createOK(scheduleService.getSchedule(no));
  }

  @PostMapping(value = "/schedule")
  public ApiResponseDto addSchedule(@RequestBody ScheduleAddRequestDto requestDto) {
    return ApiResponseDto.createOK(scheduleService.addSchedule(requestDto));
  }

  @PutMapping(value = "/schedule/{no}")
  public ApiResponseDto editSchedule(
      @PathVariable("no") long no, @RequestBody ScheduleEditRequestDto requestDto) {
    return ApiResponseDto.createOK(scheduleService.editSchedule(no, requestDto));
  }

  @DeleteMapping(value = "/schedule/{no}")
  public ApiResponseDto removeSchedule(@PathVariable("no") long no) {
    scheduleService.removeSchedule(no);

    return ApiResponseDto.createOK(true);
  }

  @PutMapping(value = "/schedule/{no}/working")
  public ApiResponseDto workingSchedule(@PathVariable("no") long no) {
    return ApiResponseDto.createOK(scheduleService.workingSchedule(no));
  }

  @PutMapping(value = "/schedule/{no}/quitting")
  public ApiResponseDto quittingSchedule(@PathVariable("no") long no) {
    return ApiResponseDto.createOK(scheduleService.quittingSchedule(no));
  }
}
