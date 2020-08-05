package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.member.MemberAddRequestDto;
import com.dreamsecurity.shopface.dto.member.MemberEditRequestDto;
import com.dreamsecurity.shopface.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@RestController
public class MemberApiController {
  private final MemberService memberService;

  @GetMapping(value = "/member")
  public ResponseEntity getMemberList() {
    return ResponseEntity.ok().body(memberService.getMemberList());
  }

  @GetMapping(value = "/member/{id}")
  public ResponseEntity getMember(@PathVariable("id") @NotBlank String id) {
    return ResponseEntity.ok().body(memberService.getMember(id));
  }

  @PostMapping(value = "/member")
  public ResponseEntity addMember(@RequestBody @Valid MemberAddRequestDto requestDto) {
    return ResponseEntity.ok().body(memberService.addMember(requestDto));
  }

  @PutMapping(value = "/member/{id}")
  public ResponseEntity editMember(
      @PathVariable("id") @NotBlank String id, @RequestBody @Valid MemberEditRequestDto requestDto) {
    return ResponseEntity.ok().body(memberService.editMember(id, requestDto));
  }

  @DeleteMapping(value = "/member/{id}")
  public ResponseEntity removeMember(@PathVariable("id") @NotBlank String id) {
    memberService.removeMember(id);

    return ResponseEntity.ok().body(true);
  }
}
