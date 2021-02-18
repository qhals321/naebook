package com.nadev.naebook.config;

import com.nadev.naebook.auth.CustomOAuth2UserService;
import com.nadev.naebook.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
        .antMatchers("/api/v1/**").hasRole(Role.USER.name())
        .anyRequest().authenticated()
        .and()
        .logout().logoutSuccessUrl("/login")
        .and()
        .formLogin().loginPage("/login")
        .and()
        .oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);
  }
}