package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Employ;
import com.dreamsecurity.shopface.dto.employ.EmployListResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.dreamsecurity.shopface.domain.QBranch.branch;
import static com.dreamsecurity.shopface.domain.QEmploy.employ;

@RequiredArgsConstructor
public class EmployRepositoryCustomImpl implements EmployRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<EmployListResponseDto> findAllByBranchNo(long no) {
        List<Employ> employs = jpaQueryFactory
                .selectFrom(employ)
                .leftJoin(employ.branch, branch).fetchJoin()
                .fetch();
        return employs.stream().map(e -> new EmployListResponseDto(
                e, e.getRole(), e.getDepartment()))
                .collect(Collectors.toList());
    }
}
