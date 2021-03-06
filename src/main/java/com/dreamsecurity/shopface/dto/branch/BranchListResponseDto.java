package com.dreamsecurity.shopface.dto.branch;

import com.dreamsecurity.shopface.domain.Branch;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class BranchListResponseDto {
  private long no;
  private String name;
  private String businessLicensePath;
  private String phone;
  private String state;
  private LocalDateTime registerDate;

  public BranchListResponseDto(Branch entity) {
    this.no = entity.getNo();
    this.name = entity.getName();
    this.businessLicensePath = entity.getBusinessLicensePath();
    this.phone = entity.getPhone();
    this.state = entity.getState();
    this.registerDate = entity.getRegisterDate();
  }
}
