package com.nadev.naebook.account.model;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.nadev.naebook.account.AccountController;
import com.nadev.naebook.domain.Account;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@Getter @Setter
public class AccountModel extends EntityModel<Account> {

  private Long id;
  private String name;
  private String email;
  private String picture;
  private String bio;

  public AccountModel(Account account) {
    this.id = account.getId();
    this.name = account.getName();
    this.email = account.getEmail();
    this.picture = account.getPicture();
    this.bio = account.getBio();
    add(WebMvcLinkBuilder.linkTo(methodOn(AccountController.class).accountById(id)).withSelfRel());
  }
}

