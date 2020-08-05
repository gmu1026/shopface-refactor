package com.dreamsecurity.shopface.dto.member;

import com.dreamsecurity.shopface.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class MemberEditRequestDto {
  @NotNull(message = "비밀번호를 입력해주세요")
  private String password;

  @NotNull(message = "주소를 입력해주세요")
  private String address;

  @NotNull(message = "주소를 입력해주세요")
  private String detailAddress;

  @NotNull(message = "우편번호를 입력해주세요")
  private String zipCode;

  @NotNull(message = "이메일을 입력해주세요")
  private String email;

  private String bankName;

  private String accountNum;

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
