package com.nadev.naebook.auth.dto;

import com.nadev.naebook.domain.user.User;
import java.io.Serializable;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SessionUser implements Serializable {

  private Long id;
  private String name;
  private String email;
  private String picture;
  private String bio;

  public SessionUser(User user) {
    this.name = user.getName();
    this.email = user.getEmail();
    this.picture = user.getPicture();
    this.bio = user.getBio();
    this.id = user.getId();
  }

  public User toUserEntity() {
    return User.builder()
        .email(email)
        .name(name)
        .id(id)
        .picture(picture)
        .bio(bio)
        .build();

  }
}