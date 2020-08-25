package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository<Record, Long>, RecordRepositoryCustom {
    Optional<Record> findByMemberId(String memberId);
    List<Record> findAllByMemberId(String memberId);
}
