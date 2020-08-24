package com.dreamsecurity.shopface.dto.availabletime;

import com.dreamsecurity.shopface.domain.AvailableTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AvailableTimeListResponseDto {
    //TODO 사업자와 근무자간 리스트가 상이함
    // 사업자: 사업장에 속한 근무자들의 가용시간표 근무자명이 필요함
    // 근무자: 자기자신의 가용시간표 (같은 지점 내 다른 근무자들의 가용시간도 알아야 스케줄을 교환 요청할 수 있다)

    // 사업자의 모든 사업장용 response :
    // 가용시간 번호, 사업장 번호, 근무자 id, 근무자 명, 시작 시간, 종료 시간

    // 근무자의 근무자가 속한 사업장의 모든 근무자의 가용시간 조회 용 response
    // 가용시간 번호, 사업장 번호, 근무자 id, 근무자 명, 시작 시간, 종료 시간

    // 근무자의 자기 자신의 모든 가용시간 조회용 response :
    // 가용시간 번호, 사업장 번호, 근무자 id?, 시작 시간, 종료 시간
    private Long no;
    private Long branchNo;
    private String memberId;
    private String memberName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public AvailableTimeListResponseDto(AvailableTime entity) {
        this.no = entity.getNo();
        this.branchNo = entity.getBranch().getNo();
        this.memberId = entity.getMember().getId();
        this.memberName = entity.getMember().getName();
        this.startTime = entity.getStartTime();
        this.endTime = entity.getEndTime();
    }
}
