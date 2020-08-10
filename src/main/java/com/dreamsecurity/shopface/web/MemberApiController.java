package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.member.MemberAddRequestDto;
import com.dreamsecurity.shopface.dto.member.MemberEditRequestDto;
import com.dreamsecurity.shopface.response.ApiResponseDto;
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
  public ApiResponseDto getMemberList() {
    return ApiResponseDto.createOK(memberService.getMemberList());
  }

  @GetMapping(value = "/member/{id}")
  public ApiResponseDto getMember(@PathVariable("id") @NotBlank String id) {
    return ApiResponseDto.createOK(memberService.getMember(id));
  }

  @PostMapping(value = "/member")
  public ApiResponseDto addMember(@RequestBody @Valid MemberAddRequestDto requestDto) {
    return ApiResponseDto.createOK(memberService.addMember(requestDto));
  }

  @PutMapping(value = "/member/{id}")
  public ApiResponseDto editMember(
      @PathVariable("id") @NotBlank String id, @RequestBody @Valid MemberEditRequestDto requestDto) {
    return ApiResponseDto.createOK(memberService.editMember(id, requestDto));
  }

  @DeleteMapping(value = "/member/{id}")
  public ApiResponseDto removeMember(@PathVariable("id") @NotBlank String id) {
    memberService.removeMember(id);

    return ApiResponseDto.createOK(true);
  }
}
