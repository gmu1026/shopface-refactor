package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.dto.availabletime.AvailableTimeAddRequestDto;
import com.dreamsecurity.shopface.dto.availabletime.AvailableTimeEditRequestDto;
import com.dreamsecurity.shopface.service.AvailableTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AvailableTimeApiController {
    private final AvailableTimeService availableTimeService;

    @GetMapping(value = "/availabletime/{no}")
    public ResponseEntity getAvailableTime(@PathVariable("no") long no) {
        return ResponseEntity.ok().body(availableTimeService.getAvailableTime(no));
    }

    @GetMapping(value = "/member/{id}/availabletime")
    public ResponseEntity getAvailableTimeList(@PathVariable("id") String memberId) {
        return ResponseEntity.ok().body(availableTimeService.getAvailableTimeList(memberId));
    }

    @PostMapping(value = "/availabletime")
    public ResponseEntity addAvailableTime(@RequestBody AvailableTimeAddRequestDto requestDto) {
        return ResponseEntity.ok().body(availableTimeService.addAvailableTime(requestDto));
    }

    @PutMapping(value = "/availabletime/{no}")
    public ResponseEntity editAvailableTime(@PathVariable("no") long no,
                                            @RequestBody AvailableTimeEditRequestDto requestDto) {
        return ResponseEntity.ok().body(availableTimeService.editAvailableTime(no, requestDto));
    }

    @DeleteMapping(value = "/availabletime/{no}")
    public ResponseEntity removeAvailableTime(@PathVariable("no") long no) {
        availableTimeService.removeAvailableTime(no);

        return ResponseEntity.ok().body(true);
    }
}
