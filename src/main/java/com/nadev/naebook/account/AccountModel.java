package com.nadev.naebook.account;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;

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
    add(linkTo(methodOn(AccountController.class).account(id)).withSelfRel());
  }
}

