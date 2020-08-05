package com.dreamsecurity.shopface.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Branch extends BaseTimeEntity {
  @PrePersist
  public void setDefaultState() {
    this.state = this.state == null ? "W" : this.state;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long no;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @Column(nullable = false, length = 100)
  private String name;

  @Column(nullable = false, length = 100)
  private String phone;

  @Column(nullable = false, length = 300)
  private String address;

  @Column(nullable = false, length = 300)
  private String detailAddress;

  @Column(nullable = false, length = 5)
  private String zipCode;

  @Column(nullable = true, length = 1000, insertable = false)
  private String businessLicensePath;

  @Column(nullable = false, length = 1)
  private String state;

  @Builder
  public Branch(
      String name,
      String phone,
      String address,
      String detailAddress,
      String zipCode,
      String businessLicensePath,
      String state,
      Member member) {
    this.name = name;
    this.phone = phone;
    this.address = address;
    this.detailAddress = detailAddress;
    this.zipCode = zipCode;
    this.businessLicensePath = businessLicensePath;
    this.state = state;
    this.member = member;
  }

  public void update(
      String name,
      String address,
      String detailAddress,
      String zipCode,
      String businessLicensePath) {
    this.name = name;
    this.address = address;
    this.detailAddress = detailAddress;
    this.zipCode = zipCode;
    this.businessLicensePath = businessLicensePath;
  }

  public void confirm() {
    this.state = "Y";
  }
}
