package com.nadev.naebook.account;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nadev.naebook.domain.Account;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
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

  @Autowired
  private TestUser testUser;

  @AfterEach
  private void afterEach() {
    accountRepository.deleteAll();
  }

  @Test
  @DisplayName("회원 검색에 실패한다.")
  void search_notFound() throws Exception {
    mockMvc.perform(get("/api/account/-1000")
        .with(oauth2Login())
    )
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("회원 검색 성공")
  void search_found() throws Exception {
    Account account = testUser.getAccount();
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

  @Test
  @DisplayName("로그인 되어있는 회원 검색")
  void search_self() throws Exception{
    Account account = testUser.getAccount();
    mockMvc.perform(get(BASE_URL+"/account/me")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("email").value(account.getEmail()))
        .andExpect(jsonPath("picture").value(account.getPicture()))
        .andExpect(jsonPath("_links.self").exists())
        .andExpect(jsonPath("_links.update-account").exists());
  }
}