package com.nadev.naebook.account;

import com.nadev.naebook.account.auth.PrincipalDetails;
import com.nadev.naebook.account.auth.Role;
import com.nadev.naebook.domain.Account;
import com.nadev.naebook.domain.AccountTag;
import com.nadev.naebook.domain.Tag;
import com.nadev.naebook.library.AccountBookRepository;
import com.nadev.naebook.library.TagRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "prototype")
public class TestUser {

  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private TagRepository tagRepository;
  @Autowired
  private AccountTagRepository accountTagRepository;
  @Autowired
  private AccountBookRepository accountBookRepository;
  @Autowired
  private RelationRepository relationRepository;

  private Account account;
  private OAuth2User oAuth2User;
  private List<Tag> tags = new ArrayList<>();

  @PostConstruct
  private void postConstruct() {
    Account account = createAccount();
    List<Tag> tags = createTags();
    tags.forEach(tag -> this.tags.add(tagRepository.save(tag)));
    this.account = accountRepository.save(account);

    this.tags.forEach(tag -> {
      accountTagRepository.save(AccountTag.of(this.account, tag));
      tag.increaseInterest();

    });

    this.oAuth2User = new PrincipalDetails(this.account, new HashMap<>());
  }

  private Account createAccount() {
    return Account.builder()
        .picture("picture123")
        .bio("hello, this is test")
        .email("test@gmail.com")
        .name("test")
        .role(Role.USER)
        .build();
  }

  private List<Tag> createTags() {
    Tag tag1 = Tag.from("test-tag1");
    Tag tag2 = Tag.from("test-tag2");
    Tag tag3 = Tag.from("test-tag3");
    Tag tag4 = Tag.from("test-tag4");
    return Arrays.asList(tag1, tag2, tag3, tag4);
  }

  public void deleteAll() {
    relationRepository.deleteAll();
    accountBookRepository.deleteAll();
    accountTagRepository.deleteAll();
    accountRepository.deleteAll();
    tagRepository.deleteAll();
  }

  public OAuth2User getAuth2User() {
    return oAuth2User;
  }

  public Account getAccount() {
    return account;
  }

  public List<Tag> getTags() {
    return tags;
  }
}
