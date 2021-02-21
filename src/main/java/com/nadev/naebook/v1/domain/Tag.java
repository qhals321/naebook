package com.nadev.naebook.v1.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

  @GeneratedValue @Id
  @Column(name = "tag_id")
  private Long id;

  private String title;

  public static Tag of(String title) {
    Tag tag = new Tag();
    tag.title = title;
    return tag;
  }

  public String title() {
    return title;
  }

  public boolean equals(String title) {
    return this.title.equals(title);
  }
}
