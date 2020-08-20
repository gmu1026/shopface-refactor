package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.*;
import com.dreamsecurity.shopface.dto.employ.EmployListResponseDto;
import com.dreamsecurity.shopface.dto.occupation.OccupationResponseDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleAddRequestDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleEditRequestDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleListResponseDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleResponseDto;
import com.dreamsecurity.shopface.enums.ScheduleColor;
import com.dreamsecurity.shopface.enums.ScheduleState;
import com.dreamsecurity.shopface.repository.*;
import com.dreamsecurity.shopface.response.ApiException;
import com.dreamsecurity.shopface.response.ApiResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class ScheduleServiceImpl implements  ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final MemberRepository memberRepository;
    private final BranchRepository branchRepository;
    private final EmployRepository employRepository;
    private final OccupationRepository occupationRepository;
    private final AlarmRepository alarmRepository;
    private final RecordRepository recordRepository;

//    @Transactional(readOnly = true)
//    public boolean isOccupationNoChecked(Occupation occupation, Branch branch, String requestColor) {
//        OccupationResponseDto existOccupation = occupationRepository.findByNoAndBranchNo(occupation.getNo(), branch.getNo());
//
//        log.info(existOccupation.getName());
//        if (existOccupation != null) {
//            for (ScheduleColor color : ScheduleColor.values()) {
//                log.info(color.getColorCode());
//                if (color.getColorCode().equals(requestColor)) {
//                    return true;
//                }
//            }
//            new IllegalArgumentException("등록할 수 없는 색상입니다.");
//            return false;
//        }else {
//            new IllegalArgumentException("업무 명이 잘못되었습니다.");
//            return false;
//        }
//    }
//
//    @Transactional(readOnly = true)
//    public boolean checkEmploy (Member member, Branch branch) {
//        boolean result = false;
//
//        List<EmployListResponseDto> entity = employRepository.findByMemberIdAndBranchNo(
//                member.getId(), branch.getNo());
//        if(!entity.isEmpty()) {
//            result = true;
//        } else {
//            result = false;
//        }
//
//        return result;
//    }

    @Transactional
    @Override
    public Long addSchedule(ScheduleAddRequestDto requestDto) {
        Long result = 0L;

        Employ employ = employRepository.findById(requestDto.getEmployNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 근무자는 없습니다"));

        Member member = memberRepository.findById(employ.getMember().getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다"));

        Branch branch = branchRepository.findById(employ.getBranch().getNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 지점이 없습니다"));

        Occupation occupation = occupationRepository.findById(requestDto.getOccupationNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 업무가 없습니다"));

    if (requestDto.getWorkStartTime().toLocalDate().isBefore(LocalDate.now())) {
        throw new ApiException(ApiResponseCode.BAD_REQUEST, "스케줄을 등록할 수 없는 날짜입니다");
    }
      if (scheduleRepository.existSchedule(
          requestDto.getWorkStartTime(), requestDto.getWorkEndTime(), member.getId())) {
           requestDto.setMember(member);
           requestDto.setBranch(branch);
           requestDto.setOccupation(occupation);
           result = scheduleRepository.save(requestDto.toEntity()).getNo();

           Alarm alarm = Alarm.builder()
                   .member(member)
                   .contents(branch.getName() + " 지점에서 스케줄이 등록되었습니다. "
                           + requestDto.getWorkStartTime() + "부터 " +
                           requestDto.getWorkEndTime())
                   .type("ADD_SCHEDULE")
                   .build();
           alarmRepository.save(alarm);
       } else {
           throw new ApiException(ApiResponseCode.BAD_REQUEST, "요청한 시간에 스케줄이 이미 존재합니다");
       }

        return result;
    }

    @Transactional(readOnly = true)
    @Override
    public List<ScheduleListResponseDto> getScheduleList(long no) {
        return scheduleRepository.findAllBranchNo(no);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ScheduleListResponseDto> getScheduleList(String id, long no) {
        return scheduleRepository.findAllByMemberIdAndBranchNo(id, no);
    }

    @Transactional(readOnly = true)
    @Override
    public ScheduleResponseDto getSchedule(long no) {
        Schedule entity = scheduleRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 없습니다."));

        return new ScheduleResponseDto(entity);
    }

    @Transactional
    @Override
    public Long editSchedule(long no, ScheduleEditRequestDto requestDto) {
        Schedule entity = scheduleRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 없습니다."));

        if (requestDto.getWorkStartTime().isBefore(requestDto.getWorkEndTime())) {
            if (occupationRepository.findByNoAndBranchNo(requestDto.getOccupation().getNo(), requestDto.getOccupation().getBranch().getNo()) != null) {
                for (ScheduleColor color : ScheduleColor.values()) {
                    if (color.getColorCode().equals(requestDto.getColor())) {
                        for (ScheduleState state : ScheduleState.values()) {
                            if (state.getState().equals(requestDto.getState())) {
                                Alarm alarm = Alarm.builder()
                                        .member(entity.getMember())
                                        .contents(entity.getBranch().getName() + " 지점에서 스케줄이 수정되었습니다. "
                                                + entity.getWorkStartTime() + entity.getWorkEndTime() + "에서 변경됨")
                                        .type("UPDATE_SCHEDULE")
                                        .build();

                                alarmRepository.save(alarm);

                                entity.update(requestDto.getMember(), requestDto.getWorkStartTime(),
                                        requestDto.getWorkEndTime(), requestDto.getOccupation(), requestDto.getColor());

                                return no;
                            }
                        } new IllegalArgumentException("잘못된 스케줄 상태입니다.");
                    }
                } new IllegalArgumentException("잘못된 색상입니다.");
            } new IllegalArgumentException("잘못된 업무입니다.");
        } new IllegalArgumentException("종료시간이 시작시간보다 빠른 시각입니다.");

        return no;
    }

    @Transactional
    @Override
    public void removeSchedule(long no) {
        Schedule entity = scheduleRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 없습니다."));
        if (!"W".equals(entity.getState())
                && !"L".equals(entity.getState())
                && !"A".equals(entity.getState())) {
            scheduleRepository.delete(entity);

            Alarm alarm = Alarm.builder()
                    .member(entity.getMember())
                    .contents(entity.getBranch().getName() + " 지점에서 스케줄이 삭제되었습니다. "
                            + entity.getWorkStartTime() + "부터 " +
                            entity.getWorkEndTime() + "까지의 스케줄")
                    .type("REMOVE_SCHEDULE")
                    .build();
            alarmRepository.save(alarm);
        } else {
            new IllegalArgumentException("출근, 지각, 결근 상태인 스케줄은 삭제할 수 없습니다.");
        }
    }

    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void checkAbsenteeismSchedule() {
        List<Schedule> absenteeismList = scheduleRepository.findAllByToday().stream().filter(
                s -> s.getWorkEndTime().isBefore(LocalDateTime.now())
                        && "R".equals(s.getState())).collect(Collectors.toList());

        for (Schedule schedule : absenteeismList) {
            schedule.absenteeism();

            Alarm employeeAlarm = Alarm.builder()
                    .member(schedule.getMember())
                    .contents(schedule.getBranch().getName() + " 지점에서 근무가 비정상 처리되었습니다 "
                            + schedule.getWorkStartTime() + "부터 " +
                            schedule.getWorkEndTime() + "까지의 스케줄")
                    .type("ABSENTEEISM_SCHEDULE")
                    .build();

            Alarm businessmanAlarm = Alarm.builder()
                    .member(schedule.getBranch().getMember())
                    .contents(schedule.getBranch().getName() + " 지점에서 근무가 비정상 처리되었습니다 "
                            + schedule.getWorkStartTime() + "부터 " +
                            schedule.getWorkEndTime() + "까지의 스케줄")
                    .type("ABSENTEEISM_SCHEDULE")
                    .build();
            alarmRepository.save(employeeAlarm);
            alarmRepository.save(businessmanAlarm);
        }
    }

    @Transactional
    @Override
    public Boolean workingSchedule(long no) {
        Schedule schedule = scheduleRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 없습니다"));

        schedule.workingSchedule();

        Employ employ = employRepository.findByMemberIdAndBranchNo(
                schedule.getMember().getId(), schedule.getBranch().getNo());

        double betweenTime = (double) ChronoUnit.SECONDS.between(schedule.getWorkStartTime(),
                        schedule.getWorkEndTime()) /  3600;
        Record record = Record.builder()
                .branchNo(schedule.getBranch().getNo())
                .branchName(schedule.getBranch().getName())
                .branchPhone(schedule.getBranch().getPhone())
                .businessmanId(schedule.getBranch().getMember().getId())
                .businessmanName(schedule.getBranch().getMember().getName())
                .businessmanPhone(schedule.getBranch().getMember().getPhone())
                .memberId(schedule.getMember().getId())
                .memberName(schedule.getMember().getName())
                .memberPhone(schedule.getMember().getPhone())
                .occupationName(schedule.getOccupation().getName())
                .salaryPlan((long) (employ.getSalary() * betweenTime))
                .workingTime(LocalDateTime.now())
                .workStartTime(schedule.getWorkStartTime())
                .workEndTime(schedule.getWorkEndTime())
                .build();
        recordRepository.save(record);

        Alarm alarm = Alarm.builder()
                .member(schedule.getBranch().getMember())
                .contents(schedule.getBranch().getName() +
                        " 지점" + schedule.getMember().getName() +
                        " 근무자가 정상 출근하였습니다")
                .type("COMMUTE_SUCCESS")
                .build();
        alarmRepository.save(alarm);

        return true;
    }

    @Transactional
    @Override
    public Boolean quittingSchedule(long no) {
        LocalDateTime quittingTime = LocalDateTime.now();

        Schedule schedule = scheduleRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 스케줄이 없습니다"));

        Record record = recordRepository.findByMemberIdAndBranchNoAndWorkStartTime(
                schedule.getMember().getId(), schedule.getBranch().getNo(), schedule.getWorkStartTime());

        Employ employ = employRepository.findByMemberIdAndBranchNo(
                schedule.getMember().getId(), schedule.getBranch().getNo());

        schedule.quittingSchedule();

        double workTIme = (double) ChronoUnit.SECONDS.between(record.getWorkingTime(), quittingTime) / 3600;
        long salaryPay = (long) (workTIme * employ.getSalary());

        record.update(null, null, quittingTime,  salaryPay);

        Alarm alarm = Alarm.builder()
                .member(schedule.getMember())
                .contents(schedule.getBranch().getName() +
                        " 지점" + schedule.getMember().getName() +
                        " 근무자가 정상 퇴근하였습니다")
                .type("COMMUTE_SUCCESS")
                .build();
        alarmRepository.save(alarm);

        return true;
    }
}
