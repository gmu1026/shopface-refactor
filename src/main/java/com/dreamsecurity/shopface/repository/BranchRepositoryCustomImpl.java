package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.QBranch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BranchRepositoryCustomImpl implements BranchRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Branch> findAllByMemberId(String memberId) {
        return queryFactory
                .selectFrom(QBranch.branch)
                .where(QBranch.branch.member.id.eq(memberId))
                .fetch();
    }
}
