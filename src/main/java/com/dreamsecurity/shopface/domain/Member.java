package com.dreamsecurity.shopface.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Member extends BaseTimeEntity {
    // TODO Address, DetailAddress, ZipCode => Embedded
    @Id
    private String id;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String phone;

    @Column(nullable = true, length = 100)
    private String email;

    @Column(nullable = true, length = 100)
    private String bankName;

    @Column(nullable = true, length = 30)
    private String accountNum;

    @Column(nullable = false, length = 1)
    private String state;

    @Column(nullable = false, length = 1)
    private String type;

    @Column(nullable = true, length = 300)
    private String address;

    @Column(nullable = true, length = 300)
    private String detailAddress;

    @Column(nullable = true, length = 5)
    private String zipCode;

//    @OneToMany
//    private List<Alarm> alarms;

    @Builder
    public Member(String id, String password, String name,
                  String phone, String email, String bankName, String accountNum,
                  String address, String state, String type,
                  String detailAddress, String zipCode) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.bankName = bankName;
        this.accountNum = accountNum;
        this.address = address;
        this.state = state;
        this.type = type;
        this.detailAddress = detailAddress;
        this.zipCode = zipCode;
    }

    public void update(String password, String address, String detailAddress,
                       String zipCode, String email, String bankName, String accountNum) {
        this.password = password;
        this.address = address;
        this.detailAddress = detailAddress;
        this.zipCode = zipCode;
        this.email = email;
        this.bankName = bankName;
        this.accountNum = accountNum;
    }
}
