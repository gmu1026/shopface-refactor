package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.record.RecordAddRequestDto;
import com.dreamsecurity.shopface.dto.record.RecordEditRequestDto;
import com.dreamsecurity.shopface.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class RecordApiController {
  private final RecordService recordService;

  @GetMapping(value = "/record/member/{id}")
  public ResponseEntity getRecordList(@PathVariable("id") String memberId) {
    return ResponseEntity.ok().body(recordService.getRecordList(memberId));
  }

  @PostMapping(value = "/record")
  public ResponseEntity addRecord(@RequestBody RecordAddRequestDto requestDto) {
    return ResponseEntity.ok().body(recordService.addRecord(requestDto));
  }

  @PutMapping(value = "/record/{no}")
  public ResponseEntity editRecord(
      @PathVariable("no") long no, @RequestBody RecordEditRequestDto requestDto) {
    return ResponseEntity.ok().body(recordService.editRecord(no, requestDto));
  }

  @DeleteMapping(value = "/record/{no}")
  public ResponseEntity removeRecord(@PathVariable("no") long no) {
    recordService.removeRecord(no);

    return ResponseEntity.ok().body(true);
  }
}
