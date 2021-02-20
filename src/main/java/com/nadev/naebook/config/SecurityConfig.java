package com.nadev.naebook.config;

import com.nadev.naebook.auth.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  private final CustomOAuth2UserService customOAuth2UserService;
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .headers().frameOptions().disable()
        .and()
        .authorizeRequests()
        .antMatchers("/login", "/css/**", "/img/**", "/js/**", "/h2/**", "/h2-console/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .logout().logoutSuccessUrl("/login")
        .and()
        .formLogin().loginPage("/login")
        .and()
        .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
        .antMatchers("/swagger-ui.html", "/swagger-ui/**","/v3/api-docs/**");
  }
}