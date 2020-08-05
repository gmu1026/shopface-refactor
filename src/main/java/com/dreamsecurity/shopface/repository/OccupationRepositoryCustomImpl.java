package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.dto.occupation.OccupationListResponseDto;
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
}
