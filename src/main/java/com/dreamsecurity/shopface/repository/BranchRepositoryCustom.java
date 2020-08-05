package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Member;

import java.util.List;

public interface BranchRepositoryCustom {
    List<Branch> findAllByMemberId(String memberId);
}
