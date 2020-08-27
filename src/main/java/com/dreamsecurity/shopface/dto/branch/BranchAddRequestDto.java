package com.dreamsecurity.shopface.dto.branch;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BranchAddRequestDto {
  private String name;
  private String phone;
  private String address;
  private String detailAddress;
  private String zipCode;
  private String memberId;
  private String state = "N";

  @JsonIgnore
  private Member member;

  public BranchAddRequestDto(Branch entity) {
    this.name = entity.getName();
    this.phone = entity.getPhone();
    this.address = entity.getAddress();
    this.detailAddress = entity.getDetailAddress();
    this.zipCode = entity.getZipCode();
    this.member = entity.getMember();
  }

  public Branch toEntity() {
    return Branch.builder()
            .name(this.name)
            .phone(this.phone)
            .address(this.address)
            .detailAddress(this.detailAddress)
            .zipCode(this.zipCode)
            .member(this.member)
            .state(this.state)
            .build();
  }

  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

  public void setMember(Member member) {
    this.member = member;
  }
}
