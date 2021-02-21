package com.nadev.naebook.v1.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Relation {

  @GeneratedValue @Id
  @Column(name = "rel_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "follower_id")
  private User follower;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "followee_id")
  private User followee;

  public static Relation of(User follower, User followee) {
    Relation relation = new Relation();
    relation.followee = followee;
    relation.follower = follower;
    return relation;
  }
}
