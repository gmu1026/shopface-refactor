package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Occupation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OccupationRepository extends JpaRepository<Occupation, Long>, OccupationRepositoryCustom {
}
