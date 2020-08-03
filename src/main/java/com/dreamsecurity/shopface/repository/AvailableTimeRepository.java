package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.AvailableTime;
import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailableTimeRepository extends JpaRepository<AvailableTime, Long> {
    List<AvailableTime> findAllByBranch(Branch branch);
    List<AvailableTime> findAllByMember(Member member);
}
