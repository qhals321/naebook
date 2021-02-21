package com.nadev.naebook.auth.dto;

import com.nadev.naebook.domain.user.User;
import com.nadev.naebook.domain.user.UserTag;
import com.nadev.naebook.dto.ProfileRequestDto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
  private List<String> tags;

  public SessionUser(User user) {
    this.name = user.getName();
    this.email = user.getEmail();
    this.picture = user.getPicture();
    this.bio = user.getBio();
    this.id = user.getId();
    this.tags = user.getUserTags()
        .stream()
        .map(UserTag::title)
        .collect(Collectors.toList());
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

  public void addTag(String title) {
    tags.add(title);
  }

  public void changeProfile(ProfileRequestDto requestDto) {
    this.bio = requestDto.getBio();
    this.picture = requestDto.getPicture();
    this.name = requestDto.getName();
  }
}