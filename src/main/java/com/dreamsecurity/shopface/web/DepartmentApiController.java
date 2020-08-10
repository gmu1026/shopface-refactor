package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.department.DepartmentAddRequestDto;
import com.dreamsecurity.shopface.dto.department.DepartmentEditRequestDto;
import com.dreamsecurity.shopface.response.ApiResponseDto;
import com.dreamsecurity.shopface.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class DepartmentApiController {
  private final DepartmentService departmentService;

  @GetMapping(value = "/branch/{no}/department")
  public ApiResponseDto getDepartmentList(@PathVariable("no") long no) {
    return ApiResponseDto.createOK(departmentService.getDepartmentList(no));
  }

  @PostMapping(value = "/department")
  public ApiResponseDto addDepartment(@RequestBody DepartmentAddRequestDto requestDto) {
    return ApiResponseDto.createOK(departmentService.addDepartment(requestDto));
  }

  @PutMapping(value = "/department/{no}")
  public ApiResponseDto editDepartment(@PathVariable("no") long no, @RequestBody DepartmentEditRequestDto requestDto) {
    return ApiResponseDto.createOK(departmentService.editDepartment(no, requestDto));
  }

  @DeleteMapping(value = "/department/{no}")
  public ApiResponseDto removeDepartment(@PathVariable("no") long no) {
    departmentService.removeDepartment(no);

    return ApiResponseDto.createOK(true);
  }
}
