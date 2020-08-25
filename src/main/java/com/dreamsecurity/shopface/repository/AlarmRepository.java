package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long>, AlarmRepositoryCustom {
}
