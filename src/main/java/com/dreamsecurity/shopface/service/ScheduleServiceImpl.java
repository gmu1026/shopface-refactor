package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.*;
import com.dreamsecurity.shopface.dto.schedule.ScheduleAddRequestDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleEditRequestDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleListResponseDto;
import com.dreamsecurity.shopface.dto.schedule.ScheduleResponseDto;
import com.dreamsecurity.shopface.repository.*;
import com.dreamsecurity.shopface.response.ApiException;
import com.dreamsecurity.shopface.response.ApiResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class ScheduleServiceImpl implements  ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final EmployRepository employRepository;
    private final OccupationRepository occupationRepository;
    private final AlarmRepository alarmRepository;
    private final RecordRepository recordRepository;

    @Transactional
    @Override
    public Long addSchedule(ScheduleAddRequestDto requestDto) {
        Long result;

        Employ employ = employRepository.findById(requestDto.getEmployNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 근무자는 없습니다"));

        Member member = employ.getMember();

        Branch branch = employ.getBranch();

        Occupation occupation = occupationRepository.findById(requestDto.getOccupationNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 업무가 없습니다"));

        if (checkSchedule(member.getId(), requestDto.getWorkStartTime(), requestDto.getWorkEndTime())) {
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
    public List<ScheduleListResponseDto> getScheduleList(String id) {
        return scheduleRepository.findAllByMemberId(id);
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

        Employ employ = employRepository.findById(requestDto.getEmployNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 근무자가 없습니다"));

        Occupation occupation = occupationRepository.findById(requestDto.getOccupationNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 업무가 없습니다"));

        if (!checkSchedule(employ.getMember().getId(),
                requestDto.getWorkStartTime(),requestDto.getWorkEndTime())) {
            throw new ApiException(ApiResponseCode.BAD_REQUEST, "이미 스케줄이 존재합니다");
        }
        
        if ("R".equals(entity.getState())) {
            entity.update(employ.getMember(), requestDto.getWorkStartTime(),
                    requestDto.getWorkEndTime(), occupation, requestDto.getColor());

            Alarm alarm = Alarm.builder()
                    .member(entity.getMember())
                    .contents(entity.getBranch().getName() + " 지점에서 스케줄이 수정되었습니다.")
                    .type("UPDATE_SCHEDULE")
                    .build();
            alarmRepository.save(alarm);
        } else {
            throw new ApiException(ApiResponseCode.BAD_REQUEST, "수정할 수 없는 스케줄입니다");
        }

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
            throw new IllegalArgumentException("출근, 지각, 결근 상태인 스케줄은 삭제할 수 없습니다.");
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
                    .contents(schedule.getBranch().getName() + " 지점에서 근무가 비정상 처리되었습니다. "
                            + schedule.getWorkStartTime() + "부터 " +
                            schedule.getWorkEndTime() + "까지의 스케줄")
                    .type("ABSENTEEISM_SCHEDULE")
                    .build();

            Alarm businessmanAlarm = Alarm.builder()
                    .member(schedule.getBranch().getMember())
                    .contents(schedule.getBranch().getName() + " 지점에서 근무가 비정상 처리되었습니다. "
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

        if ("W".equals(schedule.getState())) {
            throw new ApiException(ApiResponseCode.BAD_REQUEST, "이미 출근한 스케줄입니다");
        }

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
                        " 근무자가 정상 출근하였습니다.")
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

        if ("C".equals(schedule.getState())) {
            throw new ApiException(ApiResponseCode.BAD_REQUEST, "이미 퇴근한 스케줄입니다");
        }

        schedule.quittingSchedule();

        double workTIme = (double) ChronoUnit.SECONDS.between(record.getWorkingTime(), quittingTime) / 3600;
        long salaryPay = (long) (workTIme * employ.getSalary());

        record.update(null, null, quittingTime,  salaryPay);

        Alarm alarm = Alarm.builder()
                .member(schedule.getMember())
                .contents(schedule.getBranch().getName() +
                        " 지점" + schedule.getMember().getName() +
                        " 근무자가 정상 퇴근하였습니다.")
                .type("COMMUTE_SUCCESS")
                .build();
        alarmRepository.save(alarm);

        return true;
    }

    private boolean checkSchedule(String memberId, LocalDateTime workStartTime, LocalDateTime workEndTime) {
        boolean isDuplicate = false;

        long requestStartTime = Timestamp.valueOf(workStartTime).getTime();
        long requestEndTime = Timestamp.valueOf(workEndTime).getTime();

        if (workStartTime.isBefore(LocalDateTime.now()) || workEndTime.isBefore(LocalDateTime.now())) {
            return isDuplicate;
        }

        List<Schedule> schedules = scheduleRepository.findAllByDateAndMemberId(
                workStartTime, workEndTime, memberId);
        if (schedules.size() == 0) {
            isDuplicate = true;
        }

        for (Schedule schedule: schedules) {
            long startTime = Timestamp.valueOf(schedule.getWorkStartTime()).getTime();
            long endTime = Timestamp.valueOf(schedule.getWorkEndTime()).getTime();

            if ((startTime <= requestStartTime && endTime >= requestStartTime) ||
                    ((startTime <= requestEndTime) && (endTime >= requestEndTime))) {
                isDuplicate = false;

                return isDuplicate;
            } else {
                isDuplicate = true;
            }
        }

        return isDuplicate;
    }
}
