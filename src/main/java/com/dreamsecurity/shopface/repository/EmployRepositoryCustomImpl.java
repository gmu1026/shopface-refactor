package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Employ;
import com.dreamsecurity.shopface.dto.employ.EmployListResponseDto;
import com.dreamsecurity.shopface.dto.employ.EmployResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dreamsecurity.shopface.domain.QBranch.branch;
import static com.dreamsecurity.shopface.domain.QEmploy.employ;
import static com.dreamsecurity.shopface.domain.QMember.member;

@RequiredArgsConstructor
public class EmployRepositoryCustomImpl implements EmployRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Employ> findMemberAndEmployById(long no) {
        return Optional.ofNullable(
                jpaQueryFactory
                        .selectFrom(employ)
                        .leftJoin(employ.member, member)
                        .fetchJoin()
                        .where(employ.no.eq(no))
                        .fetchOne());
    }

    @Override
    public List<EmployListResponseDto> findAllByBranchNo(long no) {
        List<Employ> employs = jpaQueryFactory
                .selectFrom(employ)
                .leftJoin(employ.branch, branch).fetchJoin()
                .where(employ.branch.no.eq(no))
                .fetch();
        return employs.stream().map(e -> new EmployListResponseDto(
                e, e.getRole(), e.getDepartment()))
                .collect(Collectors.toList());
    }

    @Override
    public Employ findByMemberIdAndBranchNo(String memberId, long branchNo) {
        return jpaQueryFactory
                .selectFrom(employ)
                .where(employ.branch.no.eq(branchNo).and(employ.member.id.eq(memberId)))
                .fetchOne();
    }

    @Override
    public List<Employ> findAllByBranchNoAndDepartmentNo(long branchNo, long departmentNo) {
        return jpaQueryFactory
                .selectFrom(employ)
                .where(employ.branch.no.eq(branchNo).and(employ.department.no.eq(departmentNo)))
                .fetch();
    }

    @Override
    public List<Employ> findAllByBranchNoAndRoleNo(long branchNo, long roleNo) {
        return jpaQueryFactory
                .selectFrom(employ)
                .where(employ.branch.no.eq(branchNo).and(employ.role.no.eq(roleNo)))
                .fetch();
    }

    @Override
    public Employ findByCertCode(String certCode) {
        return jpaQueryFactory
                .selectFrom(employ)
                .where(employ.certCode.eq(certCode))
                .fetchOne();
    }

    @Override
    public Boolean existCertCode(String certCode) {
        Integer result = jpaQueryFactory
                .selectOne()
                .from(employ)
                .where(employ.certCode.eq(certCode))
                .fetchFirst();

        return result != null;
    }
}
