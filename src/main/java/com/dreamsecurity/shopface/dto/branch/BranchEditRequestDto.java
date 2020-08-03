package com.dreamsecurity.shopface.dto.branch;

import com.dreamsecurity.shopface.domain.Branch;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BranchEditRequestDto {
  private String name;
  private String address;
  private String detailAddress;
  private String zipCode;
  private String businessLicensePath;

  public BranchEditRequestDto(Branch entity) {
    this.name = entity.getName();
    this.address = entity.getAddress();
    this.detailAddress = entity.getDetailAddress();
    this.zipCode = entity.getZipCode();
    this.businessLicensePath = entity.getBusinessLicensePath();
  }
}
