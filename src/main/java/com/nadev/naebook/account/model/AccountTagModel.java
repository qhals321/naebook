package com.nadev.naebook.account.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.nadev.naebook.account.AccountController;
import com.nadev.naebook.domain.AccountTag;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@Getter @Setter
public class AccountTagModel extends EntityModel<AccountTag> {

  private Long id;
  private String title;

  public AccountTagModel(AccountTag accountTag) {
    this.id = accountTag.getId();
    this.title = accountTag.tagTitle();
    add(WebMvcLinkBuilder.linkTo(methodOn(AccountController.class).findAccountTag(accountTag.getId())).withSelfRel());
  }

}
