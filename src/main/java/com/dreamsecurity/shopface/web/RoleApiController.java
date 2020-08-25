package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.role.RoleAddRequestDto;
import com.dreamsecurity.shopface.dto.role.RoleEditRequestDto;
import com.dreamsecurity.shopface.response.ApiResponseDto;
import com.dreamsecurity.shopface.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class RoleApiController {
  private final RoleService roleService;

  @GetMapping(value = "/branch/{no}/role")
  public ApiResponseDto getRoleList(@PathVariable("no") long no) {
    return ApiResponseDto.createOK(roleService.getRoleList(no));
  }

  @PostMapping(value = "/role")
  public ApiResponseDto addRole(@RequestBody RoleAddRequestDto requestDto) {
    return ApiResponseDto.createOK(roleService.addRole(requestDto));
  }

  @PutMapping(value = "/role/{no}")
  public ApiResponseDto editRole(
      @PathVariable("no") long no, @RequestBody RoleEditRequestDto requestDto) {
    return ApiResponseDto.createOK(roleService.editRole(no, requestDto));
  }

  @DeleteMapping(value = "/role/{no}")
  public ApiResponseDto removeRole(@PathVariable("no") long no) {
    roleService.removeRole(no);

    return ApiResponseDto.createOK(true);
  }
}
