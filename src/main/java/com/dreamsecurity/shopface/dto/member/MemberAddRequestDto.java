package com.dreamsecurity.shopface.dto.member;

import com.dreamsecurity.shopface.domain.Member;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class MemberAddRequestDto {
  @NotNull(message = "아이디를 입력해주세요")
  private String id;

  @NotNull(message = "비밀번호를 입력해주세요")
  private String password;

  @NotNull(message = "이름을 입력해주세요")
  private String name;

  @NotNull(message = "주소를 입력해주세요")
  private String address;

  @NotNull(message = "주소를 입력해주세요")
  private String detailAddress;

  @NotNull(message = "우편번호를 입력해주세요")
  private String zipCode;

  @NotNull(message = "이메일을 입력해주세요")
  private String email;

  @NotNull(message = "전화번호를 입력해주세요")
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
