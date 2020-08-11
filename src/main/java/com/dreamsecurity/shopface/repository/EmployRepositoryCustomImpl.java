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

    @Override
    public List<EmployListResponseDto> findByMemberIdAndBranchNo(String memberId, long branchNo) {
        return jpaQueryFactory
                .select(Projections.constructor(EmployListResponseDto.class,
                        employ.no, employ.name , employ.state, employ.salary,
                        employ.employDate, employ.role.name, employ.department.name))
                .from(employ)
                .where(employ.branch.no.eq(branchNo).and(employ.member.id.eq(memberId)))
                .fetch();
    }

    @Override
    public List<Employ> findAllByBranchNoAndDepartmentNo(long branchNo, long departmentNo) {
        return jpaQueryFactory
                .select(Projections.constructor(Employ.class,
                        employ.no, employ.name , employ.state, employ.salary,
                        employ.employDate, employ.role.name, employ.department.name))
                .from(employ)
                .where(employ.branch.no.eq(branchNo).and(employ.department.no.eq(departmentNo)))
                .fetch();
    }

    @Override
    public List<Employ> findAllByBranchNoAndRoleNo(long branchNo, long roleNo) {
        return jpaQueryFactory
                .select(Projections.constructor(Employ.class,
                        employ.no, employ.name , employ.state, employ.salary,
                        employ.employDate, employ.role.name, employ.department.name))
                .from(employ)
                .where(employ.branch.no.eq(branchNo).and(employ.role.no.eq(roleNo)))
                .fetch();
    }

    @Override
    public Employ findByCertCode(String certCode) {
        return jpaQueryFactory.selectFrom(employ).where(employ.certCode.eq(certCode)).fetchOne();
    }
}
