package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Schedule;
import com.dreamsecurity.shopface.dto.schedule.ScheduleListResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.dreamsecurity.shopface.domain.QEmploy.employ;
import static com.dreamsecurity.shopface.domain.QSchedule.schedule;

@RequiredArgsConstructor
public class ScheduleRepositoryCustomImpl implements ScheduleRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ScheduleListResponseDto> findAllByMemberId(String id) {
        return jpaQueryFactory
                .select(Projections.constructor(
                        ScheduleListResponseDto.class, schedule.no,
                        schedule.workStartTime, schedule.workEndTime,
                        schedule.color, schedule.state, schedule.branch.no,
                        schedule.branch.name, schedule.occupation.name))
                .from(schedule)
                .where(schedule.member.id.eq(id))
                .orderBy(schedule.workStartTime.asc())
                .fetch();
    }

    @Override
    public List<Schedule> findAllByToday() {
        LocalDateTime startTime = LocalDate.now().atTime(0,0, 0);
        LocalDateTime endTime = LocalDate.now().atTime(23,59, 59);

        return jpaQueryFactory
                .selectFrom(schedule)
                .where(schedule.workStartTime.between(startTime, endTime))
                .fetch();
    }

    @Override
    public List<ScheduleListResponseDto> findAllByTodayAndMemberIdAndBranchNo(LocalDateTime workStartTime, String memberId, long branchNo) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(Date.from(workStartTime.atZone(ZoneId.systemDefault()).toInstant()));
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date startDate = new Date(cal.getTimeInMillis());

        cal.add(Calendar.DATE, 1);
        Date startNextDate = new Date(cal.getTimeInMillis());

        LocalDateTime startTime = LocalDateTime.of(workStartTime.getYear(), workStartTime.getMonth(), workStartTime.getDayOfMonth(),0,0,0);
        LocalDateTime startNextTime = LocalDateTime.of(workStartTime.getYear(), workStartTime.getMonth(), workStartTime.getDayOfMonth() + 1,0,0,0);

        return jpaQueryFactory
                .select(Projections.constructor(ScheduleListResponseDto.class,
                        schedule.no, schedule.workStartTime, schedule.workEndTime, schedule.color,
                        schedule.state, schedule.member.id, schedule.branch.no))
                .from(schedule)
                .where(((schedule.workStartTime.between(startTime, startNextTime)).or(schedule.workEndTime.between(startTime, startNextTime)))
                        .and(schedule.member.id.eq(memberId))
                        .and(schedule.branch.no.eq(branchNo)))
                .fetch();
    }

    @Override
    public List<ScheduleListResponseDto> findAllBranchNo(long no) {
        return jpaQueryFactory
            .select(
                Projections.constructor(
                    ScheduleListResponseDto.class,
                    schedule.no,
                    schedule.workStartTime,
                    schedule.workEndTime,
                    schedule.color,
                    schedule.state,
                    employ.no,
                    employ.name,
                    schedule.occupation.name,
                    schedule.branch.no))
            .from(schedule).join(employ).on(schedule.member.eq(employ.member).and(employ.branch.eq(schedule.branch)))
            .where(schedule.branch.no.eq(no))
            .orderBy(schedule.workStartTime.asc())
            .fetch();
    }

    @Override
    public List<Schedule> findAllByBranchNoAndOccupationNo(long branchNo, long occupationNo) {
        return jpaQueryFactory
                .selectFrom(schedule)
                .where(schedule.branch.no.eq(branchNo).and(schedule.occupation.no.eq(occupationNo)))
                .fetch();
    }

    @Override
    public List<Schedule> findAllByDateAndMemberId(LocalDateTime startTime, LocalDateTime endTime, String memberId) {
        LocalDateTime targetStartTime = startTime.toLocalDate().atTime(0,0,0);
        LocalDateTime targetEndTime = endTime.toLocalDate().atTime(23,59, 59);

        return jpaQueryFactory
                .selectFrom(schedule)
                .where(schedule.member.id.eq(memberId))
                .where(schedule.workStartTime.between(targetStartTime, targetEndTime))
                .fetch();
    }
}
