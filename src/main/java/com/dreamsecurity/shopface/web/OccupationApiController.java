package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.occupation.OccupationAddRequestDto;
import com.dreamsecurity.shopface.dto.occupation.OccupationEditRequestDto;
import com.dreamsecurity.shopface.response.ApiResponseDto;
import com.dreamsecurity.shopface.service.OccupationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class OccupationApiController {
  private final OccupationService occupationService;

  @GetMapping(value = "/branch/{no}/occupation")
  public ApiResponseDto getOccupationList(@PathVariable("no") long no) {
    return ApiResponseDto.createOK(occupationService.getOccupationList(no));
  }

  @PostMapping(value = "/occupation")
  public ApiResponseDto addOccupation(@RequestBody OccupationAddRequestDto requestDto) {
    return ApiResponseDto.createOK(occupationService.addOccupation(requestDto));
  }

  @PutMapping(value = "/occupation/{no}")
  public ApiResponseDto editOccupation(@PathVariable("no") long no, @RequestBody OccupationEditRequestDto requestDto) {
    return ApiResponseDto.createOK(occupationService.editOccupation(no, requestDto));
  }

  @DeleteMapping(value = "/occupation/{no}")
  public ApiResponseDto removeOccupation(@PathVariable("no") long no) {
    occupationService.removeOccupation(no);

    return ApiResponseDto.createOK(true);
  }
}
