package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.alarm.AlarmAddRequestDto;
import com.dreamsecurity.shopface.dto.alarm.AlarmEditRequestDto;
import com.dreamsecurity.shopface.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    return ResponseEntity.ok().body(alarmService.addAlarm(requestDto));
  }

  @PutMapping(value = "/alarm/{no}")
  public ResponseEntity editAlarm(
      @PathVariable long no, @RequestBody AlarmEditRequestDto requestDto) {
    return ResponseEntity.ok().body(alarmService.editAlarm(no, requestDto));
  }

  @DeleteMapping(value = "/alarm/{no}")
  public ResponseEntity removeAlarm(@PathVariable("no") long no) {
    alarmService.removeAlarm(no);

    return ResponseEntity.ok().body(true);
  }
}
