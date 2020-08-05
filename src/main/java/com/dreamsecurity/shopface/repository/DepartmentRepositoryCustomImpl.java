package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.dto.department.DepartmentListResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.dreamsecurity.shopface.domain.QDepartment.department;

@RequiredArgsConstructor
public class DepartmentRepositoryCustomImpl implements DepartmentRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<DepartmentListResponseDto> findAllByBranchNo(long no) {
        return jpaQueryFactory
                .select(Projections.constructor(
                        DepartmentListResponseDto.class,
                        department.no, department.name))
                .from(department)
                .where(department.branch.no.eq(no))
                .fetch();
    }
}
