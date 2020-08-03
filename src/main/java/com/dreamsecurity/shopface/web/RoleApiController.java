package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.role.RoleAddRequestDto;
import com.dreamsecurity.shopface.dto.role.RoleEditRequestDto;
import com.dreamsecurity.shopface.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class RoleApiController {
  private final RoleService roleService;

  @GetMapping(value = "/role/branch/{no}")
  public ResponseEntity getRoleList(@PathVariable("no") long no) {
    return ResponseEntity.ok().body(roleService.getRoleList(no));
  }

  @PostMapping(value = "/role")
  public ResponseEntity addRole(@RequestBody RoleAddRequestDto requestDto) {
    return ResponseEntity.ok().body(roleService.addRole(requestDto));
  }

  @PutMapping(value = "/role/{no}")
  public ResponseEntity editRole(
      @PathVariable("no") long no, @RequestBody RoleEditRequestDto requestDto) {
    return ResponseEntity.ok().body(roleService.editRole(no, requestDto));
  }

  @DeleteMapping(value = "/role/{no}")
  public ResponseEntity removeRole(@PathVariable("no") long no) {
    roleService.removeRole(no);

    return ResponseEntity.ok().body(true);
  }
}
