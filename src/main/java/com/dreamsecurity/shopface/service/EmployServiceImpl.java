package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Department;
import com.dreamsecurity.shopface.domain.Employ;
import com.dreamsecurity.shopface.domain.Role;
import com.dreamsecurity.shopface.dto.employ.EmployAddRequestDto;
import com.dreamsecurity.shopface.dto.employ.EmployEditRequestDto;
import com.dreamsecurity.shopface.dto.employ.EmployListResponseDto;
import com.dreamsecurity.shopface.dto.employ.EmployResponseDto;
import com.dreamsecurity.shopface.repository.BranchRepository;
import com.dreamsecurity.shopface.repository.DepartmentRepository;
import com.dreamsecurity.shopface.repository.EmployRepository;
import com.dreamsecurity.shopface.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmployServiceImpl implements EmployService {
    private final EmployRepository employRepository;
    private final BranchRepository branchRepository;
    private final RoleRepository roleRepository;
    private final DepartmentRepository departmentRepository;

    @Transactional
    @Override
    public Long addEmploy(EmployAddRequestDto requestDto) {
        Branch branch = branchRepository.findById(requestDto.getBranchNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 지점이 없습니다"));

        if (requestDto.getRoleNo() > 0) {
            Role role = roleRepository.findById(requestDto.getRoleNo())
                    .orElseThrow(() -> new IllegalArgumentException("해당 역할이 없습니다"));

            requestDto.setRole(role);
        }

        if (requestDto.getDepartmentNo() > 0) {
            Department department = departmentRepository.findById(requestDto.getDepartmentNo())
                    .orElseThrow(() -> new IllegalArgumentException("해당 부서가 없습니다"));

            requestDto.setDepartment(department);
        }
        requestDto.setBranch(branch);

        return employRepository.save(requestDto.toEntity()).getNo();
    }

    @Transactional(readOnly = true)
    @Override
    public List<EmployListResponseDto> getEmployList(long no) {
        return employRepository.findAllByBranchNo(no);
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
        Role role = roleRepository.findById(requestDto.getRoleNo())
                .orElseThrow(() -> new IllegalIdentifierException("해당 역할이 없습니다"));
        Department department = departmentRepository.findById(requestDto.getDepartmentNo())
                .orElseThrow(() -> new IllegalIdentifierException("해당 부서가 없습니다"));

        entity.update(requestDto.getSalary(), role, department);

        return no;
    }

    @Transactional
    @Override
    public void removeEmploy(long no) {
        Employ entity = employRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 고용 정보가 없습니다."));

        employRepository.delete(entity);
    }
}
