package com.dreamsecurity.shopface.service;

import com.dreamsecurity.shopface.domain.Role;
import com.dreamsecurity.shopface.dto.role.RoleAddRequestDto;
import com.dreamsecurity.shopface.dto.role.RoleEditRequestDto;
import com.dreamsecurity.shopface.dto.role.RoleListResponseDto;
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

    @Transactional
    @Override
    public Long addRole(RoleAddRequestDto requestDto) {
        return roleRepository.save(requestDto.toEntity()).getNo();
    }

    @Transactional(readOnly = true)
    @Override
    public List<RoleListResponseDto> getRoleList(long no) {
        return roleRepository.findAllByBranchNo(no).stream()
                .map(RoleListResponseDto::new)
                .collect(Collectors.toList());
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

        roleRepository.delete(entity);
    }
}
