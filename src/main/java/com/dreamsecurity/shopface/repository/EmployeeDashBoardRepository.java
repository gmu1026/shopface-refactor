package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeDashBoardRepository extends JpaRepository<Department, Long>, EmployeeDashBoardRepositoryCustom{
}
