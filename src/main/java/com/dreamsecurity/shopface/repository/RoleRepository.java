package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>, RoleRepositoryCustom {
}
