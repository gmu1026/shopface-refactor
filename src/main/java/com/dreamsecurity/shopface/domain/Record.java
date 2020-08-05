package com.dreamsecurity.shopface.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class Record {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long no;

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

    @Temporal(TemporalType.TIMESTAMP)
    private Date workStartTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date workEndTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date workingTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date quittingTime;

    @Column(nullable = true)
    private Long salaryPlan;

    @Column(nullable = true)
    private Long salaryPay;

    @Column(nullable = true)
    private Long evaluation;

    @Column(nullable = true, length = 4000)
    private String note;

    @Builder
    public Record(String businessmanName, String businessmanPhone,
                  String memberId, String memberName, String memberPhone,
                  long branchNo, String branchName, String branchPhone,
                  Date workStartTime, Date workEndTime, Date workingTime,
                  Date quittingTime, long salaryPlan, long salaryPay,
                  long evaluation, String note) {
        this.businessmanName = businessmanName;
        this.businessmanPhone = businessmanPhone;
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

    public void update(String note, Date workingTime, Date quittingTime, long salaryPay) {
        this.note = note;
        this.workingTime = workingTime;
        this.quittingTime = quittingTime;
        this.salaryPay = salaryPay;
    }
}
