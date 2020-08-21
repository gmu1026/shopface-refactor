package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.alarm.AlarmAddRequestDto;
import com.dreamsecurity.shopface.response.ApiResponseDto;
import com.dreamsecurity.shopface.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AlarmApiController {
  private final AlarmService alarmService;

  @GetMapping(value = "/member/{id}/alarm")
  public ApiResponseDto getAlarmList(@PathVariable("id") String memberId) {
    return ApiResponseDto.createOK(alarmService.getAlarmLists(memberId));
  }

  @PutMapping(value = "/alarm/{no}")
  public ApiResponseDto readAlarm(@PathVariable long no) {
    return ApiResponseDto.createOK(alarmService.readAlarm(no));
  }

  @DeleteMapping(value = "/alarm/{no}")
  public ApiResponseDto removeAlarm(@PathVariable("no") long no) {
    alarmService.removeAlarm(no);

    return ApiResponseDto.createOK(true);
  }
}
