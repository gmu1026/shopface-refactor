package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Employ;
import com.dreamsecurity.shopface.dto.employ.EmployListResponseDto;

import java.util.List;

public interface EmployRepositoryCustom {
    List<EmployListResponseDto> findAllByBranchNo(long no);
    List<EmployListResponseDto>findByMemberIdAndBranchNo(String memberId, long branchNo);
    List<Employ> findAllByBranchNoAndDepartmentNo(long branchNo, long departmentNo);
    List<Employ> findAllByBranchNoAndRoleNo(long branchNo, long roleNo);
    Employ findByCertCode(String certCode);
}
