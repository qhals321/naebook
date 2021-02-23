package com.nadev.naebook.account;

import com.nadev.naebook.account.auth.PrincipalDetails;
import com.nadev.naebook.account.auth.Role;
import com.nadev.naebook.domain.Account;
import java.util.HashMap;
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
  private Account account;
  private OAuth2User oAuth2User;

  @PostConstruct
  private void post() {
    Account account = Account.builder()
        .picture("picture123")
        .bio("hello, this is test")
        .email("test@gmail.com")
        .name("test")
        .role(Role.USER)
        .build();
    this.account = accountRepository.save(account);
    this.oAuth2User = new PrincipalDetails(this.account, new HashMap<>());
  }

  public OAuth2User getAuth2User() {
    return oAuth2User;
  }

  public Account getAccount() {
    return account;
  }
}
