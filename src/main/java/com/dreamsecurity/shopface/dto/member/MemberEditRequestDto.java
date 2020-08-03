package com.dreamsecurity.shopface.dto.member;

import com.dreamsecurity.shopface.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class MemberEditRequestDto {
  private String password;
  private String address;
  private String detailAddress;
  private String zipCode;
  private String email;
  private String bankName;
  private String accountNum;

  public MemberEditRequestDto() {

  }

  public MemberEditRequestDto(Member entity) {
    this.password = entity.getPassword();
    this.address = entity.getAddress();
    this.detailAddress = entity.getDetailAddress();
    this.zipCode = entity.getZipCode();
    this.email = entity.getEmail();
    this.bankName = entity.getBankName();
    this.accountNum = entity.getAccountNum();
  }
}
