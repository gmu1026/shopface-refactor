package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessmanDashBoardRepository extends JpaRepository<Department, Long>, BusinessmanDashBoardRepositoryCustom{
}
