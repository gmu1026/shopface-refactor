package com.dreamsecurity.shopface.repository;

import com.dreamsecurity.shopface.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {
    public Optional<Member> findById(String id);
}
