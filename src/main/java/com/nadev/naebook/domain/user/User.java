package com.nadev.naebook.domain.user;

import com.nadev.naebook.domain.BaseTimeEntity;
import javax.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

  @Builder
  public User(String name, String email, String picture, Role role) {
    this.name = name;
    this.email = email;
    this.picture = picture;
    this.role = role;
  }

  public void changeProfile(String name, String picture, String bio) {
    this.name = name;
    this.picture = picture;
    this.bio = bio;
  }

  public String getRoleKey() {
    return this.role.getKey();
  }
}
