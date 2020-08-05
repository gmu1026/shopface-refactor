package com.dreamsecurity.shopface.dto.branch;

import com.dreamsecurity.shopface.domain.Branch;
import com.dreamsecurity.shopface.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
public class BranchAddRequestDto {
  private String name;
  private String phone;
  private String address;
  private String detailAddress;
  private String zipCode;
  private String state;
  private Member member;
  private String memberId;

  public BranchAddRequestDto(Branch entity) {
    this.name = entity.getName();
    this.phone = entity.getPhone();
    this.address = entity.getAddress();
    this.detailAddress = entity.getDetailAddress();
    this.zipCode = entity.getZipCode();
    this.state = entity.getState();
    this.member = entity.getMember();
  }

  public Branch toEntity() {
    return Branch.builder()
            .name(this.name)
            .phone(this.phone)
            .address(this.address)
            .detailAddress(this.detailAddress)
            .zipCode(this.zipCode)
            .state(this.state)
            .member(this.member)
            .build();
  }

  public void setMemberId(String memberId) {
    this.memberId = memberId;
  }

  public void setMember(Member member) {
    this.member = member;
  }
}
