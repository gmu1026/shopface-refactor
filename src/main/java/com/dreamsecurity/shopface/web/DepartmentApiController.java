package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.department.DepartmentAddRequestDto;
import com.dreamsecurity.shopface.dto.department.DepartmentEditRequestDto;
import com.dreamsecurity.shopface.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class DepartmentApiController {
  private final DepartmentService departmentService;

  @GetMapping(value = "/department/branch/{no}")
  public ResponseEntity getDepartmentList(@PathVariable("no") long no) {
    return ResponseEntity.ok().body(departmentService.getDepartmentList(no));
  }

  @PostMapping(value = "/department")
  public ResponseEntity addDepartment(@RequestBody DepartmentAddRequestDto requestDto) {
    return ResponseEntity.ok().body(departmentService.addDepartment(requestDto));
  }

  @PutMapping(value = "/department/{no}")
  public ResponseEntity editDepartment(@PathVariable("no") long no, @RequestBody DepartmentEditRequestDto requestDto) {
    return ResponseEntity.ok().body(departmentService.editDepartment(no, requestDto));
  }

  @DeleteMapping(value = "/department/{no}")
  public ResponseEntity removeDepartment(@PathVariable("no") long no) {
    departmentService.removeDepartment(no);

    return ResponseEntity.ok().body(true);
  }
}
