package com.dreamsecurity.shopface.dto.member;

import com.dreamsecurity.shopface.domain.Member;
import lombok.Getter;

@Getter
public class MemberResponseDto {
  private String id;
  private String name;
  private String address;
  private String detailAddress;
  private String zipCode;
  private String email;
  private String bankName;
  private String accountNum;

  public MemberResponseDto(Member entity) {
    this.id = entity.getId();
    this.name = entity.getName();
    this.address = entity.getAddress();
    this.detailAddress = entity.getDetailAddress();
    this.zipCode = entity.getZipCode();
    this.email = entity.getEmail();
    this.bankName = entity.getBankName();
    this.accountNum = entity.getAccountNum();
  }
}
