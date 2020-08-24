package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.AvailableTime;
import com.dreamsecurity.shopface.dto.availabletime.AvailableTimeListResponseDto;
import com.dreamsecurity.shopface.dto.availabletime.AvailableTimePersnalListResponseDto;
import com.dreamsecurity.shopface.dto.branch.BranchListResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.dreamsecurity.shopface.domain.QAvailableTime.availableTime;
import static com.dreamsecurity.shopface.domain.QEmploy.employ;

@RequiredArgsConstructor
public class AvailableTimeRepositoryCustomImpl implements AvailableTimeRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    //가용시간끼리 비교하려고 조회하는 메소드 . 필요한것 : 가용시간 번호, 회원 아이디, 시작시간, 종료시간
    @Override
    public List<AvailableTimePersnalListResponseDto> findAllByMemberIdAndBetweenStartDateAndEndDate(LocalDateTime start, LocalDateTime end, String memberId) {
        return queryFactory
                .select(Projections.constructor(
                        AvailableTimePersnalListResponseDto.class,
                        availableTime.no, availableTime.branch.no,
                        availableTime.member.id,
                        availableTime.startTime, availableTime.endTime))
                .from(availableTime)
                .where(availableTime.startTime.after(start)
                        .and(availableTime.endTime.before(end))
                        .and(availableTime.member.id.eq(memberId)))
                .fetch();
    }

    @Override
    public List<AvailableTimePersnalListResponseDto> findAllByMemberId(String memberId) {
        return queryFactory
                .select(Projections.constructor(
                        AvailableTimePersnalListResponseDto.class,
                        availableTime.no, availableTime.branch.no,
                        availableTime.member.id,
                        availableTime.startTime, availableTime.endTime))
                .from(availableTime)
                .where(availableTime.member.id.eq(memberId))
                .orderBy(availableTime.startTime.asc())
                .fetch();
    }


    @Override
    public List<AvailableTimeListResponseDto> findAllByBranchNo(Long branchNo) {
        return queryFactory
                .select(Projections.constructor(
                        AvailableTimeListResponseDto.class,
                        availableTime.no, availableTime.branch.no,
                        availableTime.member.id, availableTime.member.name,
                        availableTime.startTime, availableTime.endTime))
                .from(availableTime)
                .where(availableTime.branch.no.eq(branchNo))
                .fetch();
    }

    @Override
    public List<AvailableTime> findAllByMemberIdAndStartTimeAndEndTime(Long no, LocalDateTime start, LocalDateTime end, String memberId) {
        return queryFactory
                .select(Projections.constructor(
                        AvailableTime.class,
                        availableTime.no,
                        availableTime.member, availableTime.branch,
                        availableTime.startTime, availableTime.endTime))
                .from(availableTime)
                .where(availableTime.member.id.eq(memberId)
                        .and(availableTime.no.ne(no))
                        .and(availableTime.startTime.between(start, end)
                                .or(availableTime.endTime.between(start, end))
                                .or((availableTime.startTime.before(start)).and(availableTime.endTime.after(end)))))
                .fetch();
    }
}
