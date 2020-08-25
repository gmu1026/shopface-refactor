package com.dreamsecurity.shopface.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
public class AvailableTime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long no;

    @ManyToOne
    @JoinColumn
    private Member member;

    @ManyToOne
    @JoinColumn
    private Branch branch;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime startTime;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime endTime;

    @Builder
    public AvailableTime(Member member, Branch branch, LocalDateTime startTime, LocalDateTime endTime) {
        this.member = member;
        this.branch = branch;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void update(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
