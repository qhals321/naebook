package com.nadev.naebook.v1.domain.user;

import com.nadev.naebook.v1.domain.Tag;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTag {

  @GeneratedValue @Id
  @Column(name = "user_tag_id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tag_id")
  private Tag tag;

  public static UserTag of(User user, Tag tag) {
    UserTag userTag = new UserTag();
    userTag.user = user;
    userTag.tag = tag;
    return userTag;
  }

  public String title() {
    return tag.title();
  }

  public boolean equals(String title) {
    return tag.equals(title);
  }
}
