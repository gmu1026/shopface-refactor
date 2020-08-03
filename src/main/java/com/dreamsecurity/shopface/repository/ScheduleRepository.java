package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    @Query("select s from Schedule s where branch.no = :no")
    List<Schedule> findAllBranchNo(long no);
}
