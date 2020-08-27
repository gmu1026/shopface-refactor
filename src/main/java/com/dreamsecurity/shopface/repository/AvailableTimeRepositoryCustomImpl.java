package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.AvailableTime;
import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Member;
import com.dreamsecurity.shopface.dto.availabletime.AvailableTimeListResponseDto;
import com.dreamsecurity.shopface.dto.availabletime.AvailableTimePersnalListResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import static com.dreamsecurity.shopface.domain.QAvailableTime.availableTime;

@RequiredArgsConstructor
public class AvailableTimeRepositoryCustomImpl implements AvailableTimeRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<AvailableTime> findAllByMemberIdAndBetweenStartDateAndEndDate(LocalDateTime start, LocalDateTime end, String memberId) {
        return queryFactory
                .select(Projections.constructor(
                        AvailableTime.class,
                        availableTime.no, availableTime.member,
                        availableTime.branch,
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
