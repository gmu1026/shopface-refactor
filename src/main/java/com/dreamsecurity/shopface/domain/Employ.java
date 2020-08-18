package com.dreamsecurity.shopface.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Employ {
//    @PrePersist
//    public void setDefaultState() {
//        this.state = this.state == null ? "I" : this.state;
//    }
    //I = Invite

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long no;

    @ManyToOne
    @JoinColumn
    private Branch branch;

    @ManyToOne
    @JoinColumn
    private Member member;

    @OneToOne
    @JoinColumn
    private Department department;

    @OneToOne
    @JoinColumn
    private Role role;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = true, insertable = false)
    private Long salary;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = true, length = 6)
    private String certCode;

    @Column
    private LocalDateTime employDate;

    @Column
    private LocalDateTime closeDate;

    @Column(nullable = false, length = 1)
    private String state;

    @Builder
    public Employ(String name, long salary, String state, String email, Role role, Department department, Branch branch, String certCode) {
        this.name = name;
        this.salary = salary;
        this.state = state;
        this.email = email;
        this.role = role;
        this.department = department;
        this.branch = branch;
        this.certCode = certCode;
    }

    public void update(long salary, Role role, Department department) {
        if (salary > 0) {
            this.salary = salary;
        }

        if (role != null) {
            this.role = role;
        }

        if (department != null) {
            this.department = department;
        }
    }

    public void joinMember(Member member) {
        this.member = member;
        this.state = "E";
        this.certCode = null;
        this.employDate = LocalDateTime.now();
    }

    public void inviteMember(String certCode) {
        this.certCode = certCode;
        this.state = "I";
    }

    public void disabledEmployee() {
        this.state = "D";
        this.member = null;
    }
}
