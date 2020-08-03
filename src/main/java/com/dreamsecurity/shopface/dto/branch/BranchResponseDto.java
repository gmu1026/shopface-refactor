package com.dreamsecurity.shopface.dto.branch;

import com.dreamsecurity.shopface.domain.Branch;
import lombok.Getter;

@Getter
public class BranchResponseDto {
  private long no;
  private String name;
  private String phone;
  private String address;
  private String detailAddress;
  private String zipCode;

  public BranchResponseDto(Branch entity) {
    this.no = entity.getNo();
    this.name = entity.getName();
    this.phone = entity.getPhone();
    this.address = entity.getAddress();
    this.detailAddress = entity.getDetailAddress();
    this.zipCode = entity.getZipCode();
  }
}
