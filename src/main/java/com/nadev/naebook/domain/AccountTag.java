package com.nadev.naebook.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountTag {

  @Id @GeneratedValue
  @Column(name = "account_tag_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id")
  private Account account;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tag_id")
  private Tag tag;

  public static AccountTag of(Account account, Tag tag) {
    AccountTag accountTag = new AccountTag();
    accountTag.tag = tag;
    accountTag.account = account;
    return accountTag;
  }

  public void increaseInterest() {
    tag.increaseInterest();
  }

  public void decreaseInterest() {
    tag.decreaseInterest();
  }

  public String tagTitle() {
    return tag.getTitle();
  }
}
