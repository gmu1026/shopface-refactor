package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Employ;
import com.dreamsecurity.shopface.domain.Role;
import com.dreamsecurity.shopface.dto.role.RoleAddRequestDto;
import com.dreamsecurity.shopface.dto.role.RoleEditRequestDto;
import com.dreamsecurity.shopface.dto.role.RoleListResponseDto;
import com.dreamsecurity.shopface.repository.BranchRepository;
import com.dreamsecurity.shopface.repository.EmployRepository;
import com.dreamsecurity.shopface.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final BranchRepository branchRepository;
    private final EmployRepository employRepository;

    @Transactional
    @Override
    public Long addRole(RoleAddRequestDto requestDto) {
        Branch branch = branchRepository.findById(requestDto.getBranchNo())
                .orElseThrow(() -> new IllegalArgumentException("해당 지점이 없습니다"));
        requestDto.setBranch(branch);

        return roleRepository.save(requestDto.toEntity()).getNo();
    }

    @Transactional(readOnly = true)
    @Override
    public List<RoleListResponseDto> getRoleList(long no) {
        return roleRepository.findAllByBranchNo(no);
    }

    @Transactional
    @Override
    public Long editRole(long no, RoleEditRequestDto requestDto) {
        Role entity = roleRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 직급이 없습니다."));

        entity.update(requestDto.getName());

        return no;
    }

    @Transactional
    @Override
    public void removeRole(long no) {
        Role entity = roleRepository.findById(no)
                .orElseThrow(() -> new IllegalArgumentException("해당 직급이 없습니다."));
        List<Employ> results = employRepository.findAllByBranchNoAndRoleNo(entity.getBranch().getNo(), no);
        for (Employ employ : results) {
            employ.update(employ.getSalary(), null, employ.getDepartment());
        }

        roleRepository.delete(entity);
    }
}
