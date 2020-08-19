package com.dreamsecurity.shopface.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long no;

    @Column(nullable = false, length = 100)
    private String businessmanId;

    @Column(nullable = false, length = 100)
    private String businessmanName;

    @Column(nullable = false, length = 100)
    private String businessmanPhone;

    @Column(nullable = false)
    private Long branchNo;

    @Column(nullable = false, length = 100)
    private String branchName;

    @Column(nullable = false, length = 100)
    private String branchPhone;

    @Column(nullable = false, length = 100)
    private String memberId;

    @Column(nullable = false, length = 100)
    private String memberName;

    @Column(nullable = false, length = 100)
    private String memberPhone;

    @Column(nullable = false, length = 100)
    private String occupationName;

    @Column(nullable = false)
    private LocalDateTime workStartTime;

    @Column(nullable = false)
    private LocalDateTime workEndTime;

    @Column(nullable = false)
    private LocalDateTime workingTime;

    @Column(nullable = true)
    private LocalDateTime quittingTime;

    @Column(nullable = true)
    private Long salaryPlan;

    @Column(nullable = true)
    private Long salaryPay;

    @Column(nullable = true)
    private Long evaluation;

    @Column(nullable = true, length = 4000)
    private String note;

    @Builder
    public Record(String businessmanId, String businessmanName, String businessmanPhone,
                  String memberId, String memberName, String memberPhone, String occupationName,
                  long branchNo, String branchName, String branchPhone,
                  LocalDateTime workStartTime, LocalDateTime workEndTime, LocalDateTime workingTime,
                  LocalDateTime quittingTime, long salaryPlan, long salaryPay,
                  long evaluation, String note) {
        this.businessmanId = businessmanId;
        this.businessmanName = businessmanName;
        this.businessmanPhone = businessmanPhone;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberPhone = memberPhone;
        this.occupationName = occupationName;
        this.branchNo = branchNo;
        this.branchName = branchName;
        this.branchPhone = branchPhone;
        this.workStartTime = workStartTime;
        this.workEndTime = workEndTime;
        this.workingTime = workingTime;
        this.quittingTime = quittingTime;
        this.salaryPlan = salaryPlan;
        this.salaryPay = salaryPay;
        this.evaluation = evaluation;
        this.note = note;
    }

    public void update(String note, LocalDateTime workingTime, LocalDateTime quittingTime, long salaryPay) {
        this.note = note;
        this.workingTime = workingTime;
        this.quittingTime = quittingTime;
        this.salaryPay = salaryPay;
    }
}
