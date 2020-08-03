package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.Employ;
import com.dreamsecurity.shopface.dto.employ.EmployAddRequestDto;
import com.dreamsecurity.shopface.dto.employ.EmployEditRequestDto;
import com.dreamsecurity.shopface.dto.employ.EmployListResponse;
import com.dreamsecurity.shopface.dto.employ.EmployResponseDto;
import com.dreamsecurity.shopface.repository.EmployRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class EmployServiceImpl implements EmployService {
    private final EmployRepository employRepository;

    @Transactional
    @Override
    public Long addEmploy(EmployAddRequestDto requestDto) {
        return employRepository.save(requestDto.toEntity()).getNo();
    }

    @Transactional(readOnly = true)
    @Override
    public List<EmployListResponse> getEmployList(long no) {
        return employRepository.findAll().stream()
                .map(EmployListResponse::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public EmployResponseDto getEmploy(long no) {
        Employ entity = employRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 고용 정보가 없습니다."));

        return new EmployResponseDto(entity);
    }

    @Transactional
    @Override
    public Long editEmploy(long no, EmployEditRequestDto requestDto) {
        Employ entity = employRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 고용 정보가 없습니다."));

        entity.update(requestDto.getSalary(), requestDto.getRole(), requestDto.getDepartment());

        return no;
    }

    @Transactional
    @Override
    public void removeAlarm(long no) {
        Employ entity = employRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 고용 정보가 없습니다."));

        employRepository.delete(entity);
    }
}
