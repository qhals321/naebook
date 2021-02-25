package com.nadev.naebook.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Tag {

  @Id
  @GeneratedValue
  @Column(name = "tag_id")
  private Long id;

  private String title;

  private int interest;

  public static Tag from(String title) {
    Tag tag = new Tag();
    tag.title = title;
    return tag;
  }

  public void increaseInterest() {
    this.interest++;
  }

  public void decreaseInterest() {
    this.interest--;
  }

}
