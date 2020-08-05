package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.branch.BranchAddRequestDto;
import com.dreamsecurity.shopface.dto.branch.BranchEditRequestDto;
import com.dreamsecurity.shopface.service.BranchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@RestController
public class BranchApiController {
  private final BranchService branchService;

  @GetMapping(value = "/branch/member/{id}")
  public ResponseEntity getBranchList(@PathVariable("id") String memberId) {
    return ResponseEntity.ok().body(branchService.getBranchList(memberId));
  }

  @GetMapping(value = "/branch/{no}")
  public ResponseEntity getBranch(@PathVariable("no") long no) {
    return ResponseEntity.ok().body(branchService.getBranch(no));
  }

  @PostMapping(value = "/branch")
  public ResponseEntity addBranch(@RequestBody BranchAddRequestDto requestDto) {
    return ResponseEntity.ok().body(branchService.addBranch(requestDto));
  }

  @PutMapping(value = "/branch/{no}")
  public ResponseEntity editBranch(
          @PathVariable("no") long no, @RequestBody BranchEditRequestDto requestDto) throws IOException {
    return ResponseEntity.ok().body(branchService.editBranch(no, requestDto));
  }

  @DeleteMapping(value = "/branch/{no}")
  public ResponseEntity removeBranch(@PathVariable("no") long no) {
    branchService.removeBranch(no);

    return ResponseEntity.ok().body(true);
  }
}
