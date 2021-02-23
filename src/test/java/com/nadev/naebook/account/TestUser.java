package com.nadev.naebook.account;

import com.nadev.naebook.account.auth.PrincipalDetails;
import com.nadev.naebook.account.auth.Role;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class TestUser {

  public static OAuth2User auth2User() {
    return new PrincipalDetails(account(), new HashMap<>());
  }

  public static Account account() {
    return Account.builder()
        .picture("picture123")
        .bio("hello, this is test")
        .email("test@gmail.com")
        .name("test")
        .role(Role.USER)
        .build();
  }
}
