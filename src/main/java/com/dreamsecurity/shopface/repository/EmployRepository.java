package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Employ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployRepository extends JpaRepository<Employ, Long>, EmployRepositoryCustom {
}
