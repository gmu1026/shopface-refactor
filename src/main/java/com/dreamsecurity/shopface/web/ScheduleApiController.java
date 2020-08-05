package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.schedule.ScheduleAddRequestDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleEditRequestDto;
import com.dreamsecurity.shopface.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ScheduleApiController {
  private final ScheduleService scheduleService;

  @GetMapping(value = "/branch/{no}/schedule")
  public ResponseEntity getScheduleList(@PathVariable("no") long no) {
    return ResponseEntity.ok().body(scheduleService.getScheduleList(no));
  }

  @GetMapping(value = "/schedule/{no}")
  public ResponseEntity getSchedule(@PathVariable("no") long no) {
    return ResponseEntity.ok().body(scheduleService.getSchedule(no));
  }

  @PostMapping(value = "/schedule")
  public ResponseEntity addSchedule(@RequestBody ScheduleAddRequestDto requestDto) {
    return ResponseEntity.ok().body(scheduleService.addSchedule(requestDto));
  }

  @PutMapping(value = "/schedule/{no}")
  public ResponseEntity editSchedule(
      @PathVariable("no") long no, @RequestBody ScheduleEditRequestDto requestDto) {
    return ResponseEntity.ok().body(scheduleService.editSchedule(no, requestDto));
  }

  @DeleteMapping(value = "/schedule/{no}")
  public ResponseEntity removeSchedule(@PathVariable("no") long no) {
    scheduleService.removeSchedule(no);

    return ResponseEntity.ok().body(true);
  }
}
