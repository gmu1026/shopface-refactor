package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    public List<Role> findAllByBranchNo(long no);
}
