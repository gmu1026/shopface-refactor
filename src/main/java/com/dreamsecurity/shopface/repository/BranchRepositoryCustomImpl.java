package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.dto.branch.BranchListResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.dreamsecurity.shopface.domain.QBranch.branch;

@RequiredArgsConstructor
public class BranchRepositoryCustomImpl implements BranchRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<BranchListResponseDto> findAllByMemberId(String memberId) {
        return queryFactory
                .select(Projections.constructor(BranchListResponseDto.class, branch.no, branch.name, branch.businessLicensePath, branch.state, branch.registerDate))
                .from(branch)
                .where(branch.member.id.eq(memberId))
                .fetch();
    }
}
