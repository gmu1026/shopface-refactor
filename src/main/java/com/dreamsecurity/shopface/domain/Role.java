package com.dreamsecurity.shopface.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long no;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Branch branch;

    @Column(nullable = false, length = 100)
    private String name;

    @Builder
    public Role(String name, Branch branch) {
        this.name = name;
        this.branch = branch;
    }

    public void update(String name) {
        this.name = name;
    }
}
