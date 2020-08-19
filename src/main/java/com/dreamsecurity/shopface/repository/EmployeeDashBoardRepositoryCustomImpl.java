package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.dto.dashboard.businessmanDashBoard.BusinessmanDashBoardListResponseDto;
import com.dreamsecurity.shopface.dto.dashboard.employeeDashBoard.EmployeeDashBoardListRequestDto;
import com.dreamsecurity.shopface.dto.dashboard.employeeDashBoard.EmployeeDashBoardListResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import static com.dreamsecurity.shopface.domain.QEmploy.employ;
import static com.dreamsecurity.shopface.domain.QOccupation.occupation;
import static com.dreamsecurity.shopface.domain.QRecord.record;
import static com.dreamsecurity.shopface.domain.QSchedule.schedule;
import static com.dreamsecurity.shopface.domain.QBranch.branch;

@RequiredArgsConstructor
public class EmployeeDashBoardRepositoryCustomImpl implements EmployeeDashBoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<EmployeeDashBoardListResponseDto> getEmployeeDashBoardListScheduled(String memberId) {
        LocalDateTime now = LocalDateTime.now();
        return queryFactory
                .select(Projections.constructor(
                        EmployeeDashBoardListResponseDto.class,
                        branch.name, occupation.name, employ.salary,
                        schedule.workStartTime, schedule.workEndTime,
                        schedule.state
                ))
                .from(schedule)
                .leftJoin(employ).on(employ.member.id.eq(schedule.member.id)
                        .and(employ.branch.no.eq(schedule.branch.no)))
                .leftJoin(occupation).on(occupation.branch.no.eq(schedule.branch.no)
                        .and(occupation.no.eq(schedule.occupation.no)))
                .leftJoin(branch).on(branch.no.eq(schedule.branch.no))
                .where(schedule.member.id.eq(memberId)
                        .and((schedule.state.eq("R").or(schedule.state.eq("L"))))
                        .and(schedule.workStartTime.after(now)))
                .fetch();
    }

    @Override
    public List<EmployeeDashBoardListResponseDto> getEmployeeDashBoardListWorkDone(String memberId){
        LocalDateTime now = LocalDateTime.now();
        return queryFactory
                .select(Projections.constructor(
                        EmployeeDashBoardListResponseDto.class,
                        branch.name, record.occupationName, employ.salary,
                        record.workStartTime, record.workEndTime,
                        record.workingTime, record.quittingTime,
                        schedule.state
                ))
                .from(record)
                .leftJoin(employ).on(employ.member.id.eq(record.memberId)
                        .and(employ.branch.no.eq(record.branchNo)))
                .leftJoin(schedule).on(record.branchNo.eq(schedule.branch.no)
                        .and(record.memberId.eq(schedule.member.id))
                        .and(record.workStartTime.eq(schedule.workStartTime))
                        .and(record.workEndTime.eq(schedule.workEndTime)))
                .leftJoin(branch).on(record.branchNo.eq(branch.no))
                .where(record.memberId.eq(memberId)
                        .and(record.workEndTime.before(now)))
                .fetch();
    }
}
