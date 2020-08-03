package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    Optional<Alarm> findByNo(long no);
    List<Alarm> findAllByMemberId(String memberId);
}
