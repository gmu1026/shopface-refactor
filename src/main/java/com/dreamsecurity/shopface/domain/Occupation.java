package com.dreamsecurity.shopface.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Occupation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long no;

    @ManyToOne
    @JoinColumn
    private Branch branch;

    @Column(nullable = false, length = 100)
    private String name;

    @Builder
    public Occupation(String name, Branch branch) {
        this.name = name;
        this.branch = branch;
    }

    public void update(String name) {
        this.name = name;
    }
}
