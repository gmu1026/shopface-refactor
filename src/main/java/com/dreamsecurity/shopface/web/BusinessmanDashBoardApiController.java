package com.dreamsecurity.shopface.web;

import com.dreamsecurity.shopface.response.ApiResponseDto;
import com.dreamsecurity.shopface.service.BusinessmanDashBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Slf4j
@RestController
public class BusinessmanDashBoardApiController {
    private final BusinessmanDashBoardService businessmanDashBoardService;
    // 사업자용 대시보드 목록 조회
    @GetMapping(value = "/businessman/{id}/{no}/")
    public ApiResponseDto getBusinessmanDashBoardList(@PathVariable("id") String id, @PathVariable("no") Long branchNo) {
        return ApiResponseDto.createOK(null);
    }

}
