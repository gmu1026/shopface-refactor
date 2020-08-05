package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.employ.EmployAddRequestDto;
import com.dreamsecurity.shopface.dto.employ.EmployEditRequestDto;
import com.dreamsecurity.shopface.service.EmployService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class EmployApiController {
  private final EmployService employService;

  @GetMapping(value = "/employ/branch/{no}")
  public ResponseEntity getEmployList(@PathVariable("no") long no) {
    return ResponseEntity.ok().body(employService.getEmployList(no));
  }

  @GetMapping(value = "/employ/{no}")
  public ResponseEntity getEmploy(@PathVariable("no") long no) {
    return ResponseEntity.ok().body(employService.getEmploy(no));
  }

  @PostMapping(value = "/employ")
  public ResponseEntity addEmploy(@RequestBody EmployAddRequestDto requestDto) {
    return ResponseEntity.ok().body(employService.addEmploy(requestDto));
  }

  @PutMapping(value = "/employ/{no}")
  public ResponseEntity editEmploy(@PathVariable("no") long no, @RequestBody EmployEditRequestDto requestDto) {
    return ResponseEntity.ok().body(employService.editEmploy(no, requestDto));
  }

  @DeleteMapping(value = "/employ/{no}")
  public ResponseEntity removeEmploy(@PathVariable("no") long no) {
    employService.removeEmploy(no);

    return ResponseEntity.ok().body(true);
  }
}
