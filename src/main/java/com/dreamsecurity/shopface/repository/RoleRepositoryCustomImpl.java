package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.dto.role.RoleListResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.dreamsecurity.shopface.domain.QRole.role;

@RequiredArgsConstructor
public class RoleRepositoryCustomImpl implements RoleRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<RoleListResponseDto> findAllByBranchNo(long no) {
        return queryFactory.select(Projections.constructor(
                RoleListResponseDto.class, role.no, role.name))
                .from(role)
                .where(role.branch.no.eq(no))
                .fetch();
    }
}
