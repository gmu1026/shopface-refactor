package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Record;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import static com.dreamsecurity.shopface.domain.QRecord.record;

@RequiredArgsConstructor
public class RecordRepositoryCustomImpl implements RecordRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Record findByMemberIdAndBranchNoAndWorkStartTime(
            String memberId, long branchNo, LocalDateTime workStartTime) {
        return jpaQueryFactory
                .selectFrom(record)
                .where(record.memberId.eq(memberId)
                        .and(record.branchNo.eq(branchNo))
                        .and(record.workStartTime.eq(workStartTime)))
                .fetchOne();
    }
}
