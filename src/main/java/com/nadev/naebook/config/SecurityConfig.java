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
        .and().logout().logoutSuccessUrl("/login")
        .and()
          .csrf().disable()
        .oauth2Login()
//          .loginPage("/login")
          .userInfoEndpoint()
          .userService(customOAuth2Service)


    ;
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/swagger-ui.html", "/swagger-ui/**",
        "/v3/api-docs/**", "/docs/index.html");

    web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
  }
}
