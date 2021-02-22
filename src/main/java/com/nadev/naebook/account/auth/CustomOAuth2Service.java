package com.nadev.naebook.account.auth;

import com.nadev.naebook.account.Account;
import com.nadev.naebook.account.AccountRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2Service implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final AccountRepository accountRepository;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = delegate.loadUser(userRequest);

    String email = oAuth2User.getAttribute("email");
    String name = oAuth2User.getAttribute("name");
    String picture = oAuth2User.getAttribute("picture");

    Account account = Account.builder()
        .email(email)
        .name(name)
        .picture(picture)
        .role(Role.USER)
        .build();

    saveIfAbsent(account);

    return new PrincipalDetails(account, oAuth2User.getAttributes());
  }

    private Account saveIfAbsent(Account account) {
    return accountRepository.findByEmail(account.getEmail())
        .or(() -> Optional.of(accountRepository.save(account)))
        .orElseThrow();
  }
}
