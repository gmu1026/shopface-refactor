package com.dreamsecurity.shopface.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Member extends BaseTimeEntity {
    // TODO Address, DetailAddress, ZipCode => Embedded
    @PrePersist
    public void setDefaultState() {
        this.state = this.state == null ? "A" : this.state;
    }

    @Id
    private String id;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 100)
    private String phone;

    @Column(length = 100)
    private String email;

    @Column(length = 100, insertable = false)
    private String bankName;

    @Column(length = 30, insertable = false)
    private String accountNum;

    @Column(nullable = false, length = 1)
    private String state;

    @Column(nullable = false, length = 1)
    private String type;

    @Column(length = 300, insertable = false)
    private String address;

    @Column(length = 300, insertable = false)
    private String detailAddress;

    @Column(length = 5, insertable = false)
    private String zipCode;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "member")
    private List<Branch> branches;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "member")
    private List<Alarm> alarms;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "member")
    private List<Employ> employs;

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

    public void update(String address, String detailAddress,
                       String zipCode, String email, String bankName, String accountNum) {
        this.address = address;
        this.detailAddress = detailAddress;
        this.zipCode = zipCode;
        this.email = email;
        this.bankName = bankName;
        this.accountNum = accountNum;
    }

    public void confirm() {
        this.state = "A";
    }

    public void deleteAccount() {
        this.state = "D";
    }
}
