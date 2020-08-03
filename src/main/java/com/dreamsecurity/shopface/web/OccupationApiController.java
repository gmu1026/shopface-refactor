package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.occupation.OccupationAddRequestDto;
import com.dreamsecurity.shopface.dto.occupation.OccupationEditRequestDto;
import com.dreamsecurity.shopface.service.OccupationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class OccupationApiController {
  private final OccupationService occupationService;

  @GetMapping(value = "/occupation/branch/{no}")
  public ResponseEntity getOccupationList(@PathVariable("no") long no) {
    return ResponseEntity.ok().body(occupationService.getOccupationList(no));
  }

  @PostMapping(value = "/occupation")
  public ResponseEntity addOccupation(@RequestBody OccupationAddRequestDto requestDto) {
    return ResponseEntity.ok().body(occupationService.addOccupation(requestDto));
  }

  @PutMapping(value = "/occupation/{no}")
  public ResponseEntity editOccupation(@PathVariable("no") long no, @RequestBody OccupationEditRequestDto requestDto) {
    return ResponseEntity.ok().body(occupationService.editOccupation(no, requestDto));
  }

  @DeleteMapping(value = "/occupation/{no}")
  public ResponseEntity removeOccupation(@PathVariable("no") long no) {
    occupationService.removeOccupation(no);

    return ResponseEntity.ok().body(true);
  }
}
