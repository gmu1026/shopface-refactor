package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.dto.occupation.OccupationListResponseDto;
import com.dreamsecurity.shopface.dto.occupation.OccupationResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.dreamsecurity.shopface.domain.QOccupation.occupation;

@RequiredArgsConstructor
public class OccupationRepositoryCustomImpl implements OccupationRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<OccupationListResponseDto> findAllByBranchNo(long no) {
        return jpaQueryFactory
                .select(Projections.constructor(
                        OccupationListResponseDto.class,
                        occupation.no, occupation.name))
                .from(occupation)
                .where(occupation.branch.no.eq(no))
                .fetch();
    }

    @Override
    public OccupationResponseDto findByNoAndBranchNo(long no, long branchNo) {
        return jpaQueryFactory
                .select(Projections.constructor(OccupationResponseDto.class,
                        occupation.no, occupation.branch.no, occupation.name))
                .from(occupation)
                .where(occupation.no.eq(no).and(occupation.branch.no.eq(branchNo)))
                .fetchOne();
    }
}
