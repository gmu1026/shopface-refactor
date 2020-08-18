package com.dreamsecurity.shopface.dto.record;

import com.dreamsecurity.shopface.domain.Record;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class RecordEditRequestDto {
  private String note;
  private LocalDateTime workingTime;
  private LocalDateTime quittingTime;
  private long salaryPay;

  public RecordEditRequestDto(Record entity) {
    this.note = entity.getNote();
    this.workingTime = entity.getWorkingTime();
    this.quittingTime = entity.getQuittingTime();
    this.salaryPay = entity.getSalaryPay();
  }
}
