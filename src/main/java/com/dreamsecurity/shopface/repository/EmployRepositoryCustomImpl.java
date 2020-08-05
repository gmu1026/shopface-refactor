package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.dto.employ.EmployListResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.dreamsecurity.shopface.domain.QEmploy.employ;

@RequiredArgsConstructor
public class EmployRepositoryCustomImpl implements EmployRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<EmployListResponseDto> findAllByBranchNo(long no) {
        return jpaQueryFactory
                .select(Projections.constructor(EmployListResponseDto.class,
                        employ.no, employ.name , employ.state, employ.salary,
                        employ.employDate, employ.role.name, employ.department.name))
                .from(employ)
                .where(employ.branch.no.eq(no))
                .fetch();
    }
}
