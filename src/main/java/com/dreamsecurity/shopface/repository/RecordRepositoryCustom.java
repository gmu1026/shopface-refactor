package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Record;

import java.time.LocalDateTime;

public interface RecordRepositoryCustom {
    Record findByMemberIdAndBranchNoAndWorkStartTime(String memberId, long branchNo, LocalDateTime workStartTime);
}
