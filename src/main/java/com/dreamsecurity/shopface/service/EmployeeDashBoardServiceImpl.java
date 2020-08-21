package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.dto.dashboard.employeeDashBoard.EmployeeDashBoardListResponseDto;
import com.dreamsecurity.shopface.repository.EmployeeDashBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployeeDashBoardServiceImpl implements EmployeeDashBoardService{
    private final EmployeeDashBoardRepository employeeDashBoardRepository;

    @Override
    public List<EmployeeDashBoardListResponseDto> getEmployeeDashBoardListScheduled(String memberId) {
        List<EmployeeDashBoardListResponseDto> responseDtos = employeeDashBoardRepository.getEmployeeDashBoardListScheduled(memberId);

        for (EmployeeDashBoardListResponseDto responseDto : responseDtos) {
            long hoursPlan = ChronoUnit.MINUTES.between(responseDto.getWorkEndTime(), responseDto.getWorkStartTime());
            responseDto.setHoursPlan((hoursPlan / 60) + ((hoursPlan % 60) * 0.01));
            responseDto.setSalaryPlan(responseDto.getEmploySalary() * responseDto.getHoursPlan());
        }
        return responseDtos;
    }

    @Override
    public List<EmployeeDashBoardListResponseDto> getEmployeeDashBoardListWorkDone(String memberId) {
        List<EmployeeDashBoardListResponseDto> responseDtos = employeeDashBoardRepository.getEmployeeDashBoardListWorkDone(memberId);

        for (EmployeeDashBoardListResponseDto responseDto : responseDtos) {
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
