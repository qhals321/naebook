package com.nadev.naebook.domain.user;

import com.nadev.naebook.domain.BaseTimeEntity;
import com.nadev.naebook.domain.Tag;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column
  private String picture;

  @Column
  private String bio;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Role role;

  @OneToMany(mappedBy = "user")
  @Cascade(CascadeType.ALL)
  private List<UserTag> userTags = new ArrayList<>();

  @Builder
  public User(String name, String email, String picture, Role role, Long id, String bio) {
    this.name = name;
    this.email = email;
    this.picture = picture;
    this.role = role;
    this.bio = bio;
    this.id = id;
  }

  public void changeProfile(String name, String picture, String bio) {
    this.name = name;
    this.picture = picture;
    this.bio = bio;
  }

  public void addTag(Tag tag) {
    userTags.add(UserTag.of(this, tag));
  }

  public String getRoleKey() {
    return this.role.getKey();
  }

  public boolean contains(String tagTitle) {
   return userTags
       .stream()
       .anyMatch(tag -> tag.equals(tagTitle));
  }
}
