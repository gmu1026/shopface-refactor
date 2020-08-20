package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Employ;
import com.dreamsecurity.shopface.dto.employ.EmployListResponseDto;
import com.dreamsecurity.shopface.dto.employ.EmployResponseDto;

import java.util.List;
import java.util.Optional;

public interface EmployRepositoryCustom {
    List<EmployListResponseDto> findAllByBranchNo(long no);
    Employ findByMemberIdAndBranchNo(String memberId, long branchNo);
    List<Employ> findAllByBranchNoAndDepartmentNo(long branchNo, long departmentNo);
    List<Employ> findAllByBranchNoAndRoleNo(long branchNo, long roleNo);
    Employ findByCertCode(String certCode);
    Optional<Employ> findMemberAndEmployById(long no);
}
