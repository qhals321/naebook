package com.nadev.naebook.account;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class AccountControllerTest {

  private static final String BASE_URL = "/api";
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private AccountRepository accountRepository;

  @BeforeEach
  void setUp() {
    Account account = TestUser.account();
    accountRepository.save(account);
  }

  @Test
  void search_notFound() throws Exception {
    mockMvc.perform(get("/api/account/-1000")
        .with(oauth2Login())
    )
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  void search_found() throws Exception {
    Account account = accountRepository.findByEmail(TestUser.account().getEmail()).orElseThrow();
    mockMvc.perform(get(BASE_URL+"/account/"+account.getId())
        .with(oauth2Login())
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("email").value(account.getEmail()))
        .andExpect(jsonPath("picture").value(account.getPicture()))
        .andExpect(jsonPath("_links.self").exists())
        .andExpect(jsonPath("_links.update-account").exists());
  }
}