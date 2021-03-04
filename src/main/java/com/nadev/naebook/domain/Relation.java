package com.nadev.naebook.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Relation {

    @Id
    @GeneratedValue
    @Column(name = "relation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private Account follower;

    @ManyToOne
    @JoinColumn(name = "followee_id")
    private Account followee;

    public static Relation of(Account follower, Account followee) {
        Relation relation = new Relation();
        relation.follower = follower;
        relation.followee = followee;
        return relation;
    }
}
