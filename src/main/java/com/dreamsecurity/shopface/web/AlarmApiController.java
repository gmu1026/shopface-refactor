package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.domain.Alarm;
import com.dreamsecurity.shopface.dto.alarm.AlarmAddRequestDto;
import com.dreamsecurity.shopface.dto.alarm.AlarmListResponseDto;
import com.dreamsecurity.shopface.response.ApiResponseDto;
import com.dreamsecurity.shopface.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AlarmApiController {
  private final AlarmService alarmService;

  @GetMapping(value = "/member/{id}/alarm")
  public ResponseEntity getAlarmList(@PathVariable("id") String memberId) {
    return ResponseEntity.ok().body(alarmService.getAlarmList(memberId));
  }

  @PostMapping(value = "/alarm")
  public ResponseEntity addAlarm(@RequestBody AlarmAddRequestDto requestDto) {
    // TODO 필요없다고 생각 로직에 의해서 등록이 되는 것이지 외부 요청에 의해 등록되는 것이 아님 API는 외부와 노출될 수 밖에 없음
    return ResponseEntity.ok().body(alarmService.addAlarm(requestDto));
  }

  @PutMapping(value = "/alarm/{no}")
  public ResponseEntity readAlarm(@PathVariable long no) {
    return ResponseEntity.ok().body(alarmService.readAlarm(no));
  }

  @DeleteMapping(value = "/alarm/{no}")
  public ResponseEntity removeAlarm(@PathVariable("no") long no) {
    alarmService.removeAlarm(no);

    return ResponseEntity.ok().body(true);
  }

  @GetMapping(value = "/member/{id}/alarms")
  public ApiResponseDto<List<AlarmListResponseDto>> getAlarmLists(@PathVariable("id") String memberId) {
    return ApiResponseDto.createOK(alarmService.getAlarmLists(memberId));
  }
}
