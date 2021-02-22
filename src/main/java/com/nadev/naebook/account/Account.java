package com.nadev.naebook.account;

import com.nadev.naebook.account.auth.Role;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Account {
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

//  @OneToMany(mappedBy = "user")
//  @Cascade(CascadeType.ALL)
//  private List<UserTag> userTags = new ArrayList<>();
}