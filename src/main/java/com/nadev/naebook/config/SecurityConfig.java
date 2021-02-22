package com.nadev.naebook.config;

import com.nadev.naebook.account.auth.CustomOAuth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final CustomOAuth2Service customOAuth2Service;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
          .anyRequest().authenticated()
        .and()
        .oauth2Login()
//          .loginPage("/login")
          .userInfoEndpoint()
          .userService(customOAuth2Service);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
//    web.ignoring().mvcMatchers(""); api 문서 설정하자.
    web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }
}
