package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.Department;
import com.dreamsecurity.shopface.dto.department.DepartmentAddRequestDto;
import com.dreamsecurity.shopface.dto.department.DepartmentEditRequestDto;
import com.dreamsecurity.shopface.dto.department.DepartmentListResponseDto;
import com.dreamsecurity.shopface.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;

    @Transactional
    @Override
    public Long addDepartment(DepartmentAddRequestDto requestDto) {
        return departmentRepository.save(requestDto.toEntity()).getNo();
    }

    @Transactional(readOnly = true)
    @Override
    public List<DepartmentListResponseDto> getDepartmentList(long no) {
        return departmentRepository.findAll().stream()
                .map(DepartmentListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Long editDepartment(long no, DepartmentEditRequestDto requestDto) {
        Department entity = departmentRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 부서가 존재하지 않습니다."));

        entity.update(requestDto.getName());

        return no;
    }

    @Transactional
    @Override
    public void removeDepartment(long no) {
        Department entity = departmentRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 부서가 존재하지 않습니다."));

        departmentRepository.delete(entity);
    }
}
