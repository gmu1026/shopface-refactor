package com.dreamsecurity.shopface.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Entity
public class Employ {
    @PrePersist
    public void setDefaultState() {
        this.state = this.state == null ? "B" : this.state;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long no;

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

    @Column(nullable = true)
    private long salary;

    @Column(nullable = true, length = 6)
    private String certCode;

    @Temporal(TemporalType.TIMESTAMP)
    private Date employDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date closeDate;

    @Column(nullable = false, length = 1)
    private String state;

    @Builder
    public Employ(String name, long salary, String state, Role role, Department department, Branch branch) {
        this.name = name;
        this.salary = salary;
        this.state = state;
        this.role = role;
        this.department = department;
        this.branch = branch;
    }

    public void update(long salary, Role role, Department department) {
        this.salary = salary;
        this.role = role;
        this.department = department;
    }
}
