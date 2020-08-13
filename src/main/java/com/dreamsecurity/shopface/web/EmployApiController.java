package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.employ.EmployAcceptRequestDto;
import com.dreamsecurity.shopface.dto.employ.EmployAddRequestDto;
import com.dreamsecurity.shopface.dto.employ.EmployEditRequestDto;
import com.dreamsecurity.shopface.response.ApiResponseDto;
import com.dreamsecurity.shopface.service.EmployService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class EmployApiController {
  private final EmployService employService;

  @GetMapping(value = "/branch/{no}/employ")
  public ApiResponseDto getEmployList(@PathVariable("no") long no) {
    return ApiResponseDto.createOK(employService.getEmployList(no));
  }

  @GetMapping(value = "/employ/{no}")
  public ApiResponseDto getEmploy(@PathVariable("no") long no) {
    return ApiResponseDto.createOK(employService.getEmploy(no));
  }

  @PostMapping(value = "/employ")
  public ApiResponseDto addEmploy(@RequestBody EmployAddRequestDto requestDto) {
    return ApiResponseDto.createOK(employService.addEmploy(requestDto));
  }

  @PutMapping(value = "/employ/{no}")
  public ApiResponseDto editEmploy(@PathVariable("no") long no, @RequestBody EmployEditRequestDto requestDto) {
    return ApiResponseDto.createOK(employService.editEmploy(no, requestDto));
  }

  @DeleteMapping(value = "/employ/{no}")
  public ApiResponseDto removeEmploy(@PathVariable("no") long no) {
    employService.removeEmploy(no);

    return ApiResponseDto.createOK(true);
  }

  @GetMapping(value = "/employ/check")
  public ApiResponseDto checkCertCode(@RequestParam("certcode") String certCode) {
    if (employService.checkCode(certCode)) {
      return ApiResponseDto.createOK(true);
    } else {
      return ApiResponseDto.createOK(false);
    }
  }

  @PatchMapping(value = "/employ")
  public ApiResponseDto joiningEmployee(@RequestBody EmployAcceptRequestDto requestDto) {
    return ApiResponseDto.createOK(employService.joiningEmployee(requestDto));
  }
}
