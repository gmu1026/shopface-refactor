package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.dto.alarm.AlarmListResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.dreamsecurity.shopface.domain.QAlarm.alarm;

@RequiredArgsConstructor
public class AlarmRepositoryCustomImpl implements AlarmRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<AlarmListResponseDto> findAllByMemberIdAsc(String memberId) {
        return queryFactory.select(Projections.constructor(
                AlarmListResponseDto.class,
                alarm.no, alarm.contents, alarm.type,
                alarm.registerDate, alarm.checkState))
                .from(alarm)
                .where(alarm.member.id.eq(memberId))
                .orderBy(alarm.registerDate.asc())
                .fetch();
    }
}
