package com.dreamsecurity.shopface.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@ToString
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Branch branch;

    @ManyToOne(cascade = CascadeType.ALL)
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
    private long salary;

    @Column(nullable = true, length = 6, insertable = false)
    private String certCode;

    @Column
    private LocalDateTime employDate;

    @Column
    private LocalDateTime closeDate;

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
