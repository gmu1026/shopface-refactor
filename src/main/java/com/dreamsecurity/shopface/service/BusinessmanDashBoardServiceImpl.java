package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.dto.dashboard.businessmanDashBoard.BusinessmanDashBoardListRequestDto;
import com.dreamsecurity.shopface.dto.dashboard.businessmanDashBoard.BusinessmanDashBoardListResponseDto;
import com.dreamsecurity.shopface.repository.BusinessmanDashBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BusinessmanDashBoardServiceImpl implements BusinessmanDashBoardService{
    private final BusinessmanDashBoardRepository repository;
    @Override
    public List<BusinessmanDashBoardListResponseDto> getBusinessmanDashBoardListScheduled(BusinessmanDashBoardListRequestDto requestDto) {
        List<BusinessmanDashBoardListResponseDto> responseDtos = repository.getBusinessmanDashBoardListScheduled(requestDto);

        for (BusinessmanDashBoardListResponseDto responseDto : responseDtos) {
            long hoursPlan = ChronoUnit.MINUTES.between(responseDto.getWorkEndTime(), responseDto.getWorkStartTime());
            responseDto.setHoursPlan((hoursPlan / 60) + ((hoursPlan % 60) * 0.01));
            responseDto.setSalaryPlan(responseDto.getEmploySalary() * responseDto.getHoursPlan());
        }

        return responseDtos;
    }

    @Override
    public List<BusinessmanDashBoardListResponseDto> getBusinessmanDashBoardListWorking(BusinessmanDashBoardListRequestDto requestDto) {
        List<BusinessmanDashBoardListResponseDto> responseDtos = repository.getBusinessmanDashBoardListWorking(requestDto);
        LocalDateTime now = LocalDateTime.now();

        for (BusinessmanDashBoardListResponseDto responseDto : responseDtos) {
            long hoursPlan = ChronoUnit.MINUTES.between(responseDto.getWorkEndTime(), responseDto.getWorkStartTime());
            responseDto.setHoursPlan((hoursPlan / 60) + ((hoursPlan % 60) * 0.01));
            responseDto.setSalaryPlan(responseDto.getEmploySalary() * responseDto.getHoursPlan());

            long actualHours = ChronoUnit.MINUTES.between(now, responseDto.getWorkingTime());
            responseDto.setActualWorkingHours((actualHours / 60) + ((actualHours % 60) * 0.01));
            responseDto.setActualSalary(responseDto.getEmploySalary() * responseDto.getActualWorkingHours());
        }

        return responseDtos;
    }

    @Override
    public List<BusinessmanDashBoardListResponseDto> getBusinessmanDashBoardListWorkDone(BusinessmanDashBoardListRequestDto requestDto) {
        List<BusinessmanDashBoardListResponseDto> responseDtos = repository.getBusinessmanDashBoardListWorkDone(requestDto);

        for (BusinessmanDashBoardListResponseDto responseDto : responseDtos) {
            long hoursPlan = ChronoUnit.MINUTES.between(responseDto.getWorkEndTime(), responseDto.getWorkStartTime());
            responseDto.setHoursPlan((hoursPlan / 60) + ((hoursPlan % 60) * 0.01));
            responseDto.setSalaryPlan(responseDto.getEmploySalary() * responseDto.getHoursPlan());

            //출근처리했지만 퇴근처리는 안하고 스케줄이 끝난경우
            if (responseDto.getWorkingTime() != null && responseDto.getQuittingTime() == null) {
                long actualHours = ChronoUnit.MINUTES.between(responseDto.getWorkEndTime(), responseDto.getWorkingTime());
                responseDto.setActualWorkingHours((actualHours / 60) + ((actualHours % 60) * 0.01));
                responseDto.setActualSalary(responseDto.getEmploySalary() * responseDto.getActualWorkingHours());
            } else if (responseDto.getWorkingTime() == null && responseDto.getQuittingTime() != null) { // 출근처리는 안햇지만 퇴근처리만 한 경우
                long actualHours = ChronoUnit.MINUTES.between(responseDto.getQuittingTime(), responseDto.getWorkStartTime());
                responseDto.setActualWorkingHours((actualHours / 60) + ((actualHours % 60) * 0.01));
                responseDto.setActualSalary(responseDto.getEmploySalary() * responseDto.getActualWorkingHours());
            } else {// 출근처리와 퇴근처리를 정상적으로 한 경우
                long actualHours = ChronoUnit.MINUTES.between(responseDto.getQuittingTime(), responseDto.getWorkingTime());
                responseDto.setActualWorkingHours((actualHours / 60) + ((actualHours % 60) * 0.01));
                responseDto.setActualSalary(responseDto.getEmploySalary() * responseDto.getActualWorkingHours());
            }
        }
        return responseDtos;
    }
}
