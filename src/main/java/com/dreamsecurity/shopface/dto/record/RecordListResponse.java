package com.dreamsecurity.shopface.dto.record;

import com.dreamsecurity.shopface.domain.Record;
import lombok.Getter;

import java.util.Date;

@Getter
public class RecordListResponse {
    private long no;
    private long branchNo;
    private String branchName;
    private String branchPhone;
    private String businessmanName;
    private String businessmanPhone;
    private String memberId;
    private String memberName;
    private String memberPhone;
    private String occupationName;
    private String note;
    private Date workingTime;
    private Date quittingTime;
    private Date workStartTime;
    private Date workEndTime;
    private long salaryPlan;
    private long salaryPay;

    public RecordListResponse(Record entity) {
        this.no = entity.getNo();
        this.branchNo = entity.getBranchNo();
        this.branchName = entity.getBranchName();
        this.branchPhone = entity.getBranchPhone();
        this.businessmanName = entity.getBusinessmanName();
        this.businessmanPhone = entity.getBusinessmanPhone();
        this.memberId = entity.getMemberId();
        this.memberName = entity.getMemberName();
        this.memberPhone = entity.getMemberPhone();
        this.occupationName = entity.getOccupationName();
        this.note = entity.getNote();
        this.workingTime = entity.getWorkingTime();
        this.quittingTime = entity.getQuittingTime();
        this.workStartTime = entity.getWorkStartTime();
        this.workEndTime = entity.getWorkEndTime();
        this.salaryPlan = entity.getSalaryPlan();
        this.salaryPay = entity.getSalaryPay();
    }
}
