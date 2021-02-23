package com.nadev.naebook.account.auth;

import com.nadev.naebook.account.Account;
import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter @Setter
public class PrincipalDetails implements OAuth2User {

  private Account account;
  private Map<String, Object> attributes;

  public PrincipalDetails(Account account, Map<String, Object> attributes) {
    this.account = account;
    this.attributes = attributes;
  }

  @Override
  public Map<String, Object> getAttributes() {
    return attributes;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getName() {
    return account.getName();
  }
}
