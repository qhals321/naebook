package com.nadev.naebook.auth.dto;

import com.nadev.naebook.domain.user.User;
import java.io.Serializable;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class SessionUser implements Serializable {

  private String name, email, picture, bio;

  public SessionUser(User user) {
    this.name = user.getName();
    this.email = user.getEmail();
    this.picture = user.getPicture();
    this.bio = user.getBio();
  }
}