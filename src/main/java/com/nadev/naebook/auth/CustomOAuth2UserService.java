package com.nadev.naebook.auth;

import com.nadev.naebook.auth.dto.OAuthAttributes;
import com.nadev.naebook.auth.dto.SessionUser;
import com.nadev.naebook.domain.user.User;
import com.nadev.naebook.repository.UserRepository;
import java.util.Collections;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final UserRepository userRepository;
  private final HttpSession httpSession;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = delegate.loadUser(userRequest);
    // 현재 로그인 진행 중인 서비스를 구분하는 코드
    String registrationId = userRequest
        .getClientRegistration()
        .getRegistrationId();
    // oauth2 로그인 진행 시 키가 되는 필드값
    String userNameAttributeName = userRequest.getClientRegistration()
        .getProviderDetails()
        .getUserInfoEndpoint()
        .getUserNameAttributeName();
    // OAuthAttributes: attribute를 담을 클래스 (개발자가 생성)
    OAuthAttributes attributes = OAuthAttributes
        .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

    User user = saveIfAbsent(attributes);

    // SessioUser: 세션에 사용자 정보를 저장하기 위한 DTO 클래스 (개발자가 생성)
    httpSession.setAttribute("user", new SessionUser(user));
    return new DefaultOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
        attributes.getAttributes(),
        attributes.getNameAttributeKey()
    );
  }

  private User saveIfAbsent(OAuthAttributes attributes) {
    return userRepository.findByEmail(attributes.getEmail())
        .or(() -> Optional.of(userRepository.save(attributes.toEntity())))
        .orElseThrow(IllegalStateException::new);
  }
}
