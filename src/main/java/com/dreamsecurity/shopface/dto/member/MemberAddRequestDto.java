package com.dreamsecurity.shopface.dto.member;

import com.dreamsecurity.shopface.domain.Member;
import lombok.Getter;

@Getter
public class MemberAddRequestDto {
  private String id;
  private String password;
  private String name;
  private String address;
  private String detailAddress;
  private String zipCode;
  private String email;
  private String phone;
  private String state;
  private String type;

  public MemberAddRequestDto(String id, String password, String name,
                             String phone, String state, String type) {
    this.id = id;
    this.password = password;
    this.name = name;
    this.phone = phone;
    this.state = state;
    this.type = type;
  }

  public MemberAddRequestDto(Member entity) {
    this.id = entity.getId();
    this.password = entity.getPassword();
    this.name = entity.getName();
    this.address = entity.getAddress();
    this.detailAddress = entity.getDetailAddress();
    this.zipCode = entity.getZipCode();
    this.email = entity.getEmail();
    this.phone = entity.getPhone();
    this.type = entity.getType();
    this.state = entity.getState();
  }

  public Member toEntity() {
    return Member.builder()
            .id(this.id)
            .password(this.password)
            .name(this.name)
            .address(this.address)
            .detailAddress(this.detailAddress)
            .zipCode(this.zipCode)
            .email(this.email)
            .phone(this.phone)
            .type(this.type)
            .state(this.state)
            .build();
  }
}
