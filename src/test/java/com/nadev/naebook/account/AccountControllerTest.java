package com.nadev.naebook.account;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nadev.naebook.domain.Account;
import com.nadev.naebook.domain.AccountTag;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
  private AccountTagRepository accountTagRepository;

  @Autowired
  private TestUser testUser;

  @AfterEach
  private void afterEach() {
    testUser.deleteAll();
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

  @Test
  @DisplayName("이름을 비우고 프로필 업데이트하는 경우")
  void updateAccount_notValid() throws Exception{
    String newBio = "changed bio";
    AccountRequestDto requestDto = AccountRequestDto.builder()
        .bio(newBio)
        .build();

    mockMvc.perform(put(BASE_URL+"/account")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(requestDto))
    )
        .andDo(print())
        .andExpect(status().is4xxClientError())
        .andExpect(jsonPath("$[*].field").value(Matchers.containsInAnyOrder("name")))
        .andExpect(jsonPath("$[*].objectName").value(Matchers.containsInAnyOrder("accountRequestDto")))
        .andExpect(jsonPath("$[*].code").value(Matchers.containsInAnyOrder("NotEmpty")))
        .andExpect(jsonPath("$[*].defaultMessage").value(Matchers.containsInAnyOrder("must not be empty")));
  }

  @Test
  void updateAccount_success() throws Exception {
    String newName = "nabom";
    String newBio = "changed bio";
    String newPicture = "new picture";
    AccountRequestDto requestDto = AccountRequestDto.builder()
        .name(newName)
        .bio(newBio)
        .picture(newPicture)
        .build();

    mockMvc.perform(put(BASE_URL+"/account")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(requestDto))
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("name").value(newName))
        .andExpect(jsonPath("bio").value(newBio))
        .andExpect(jsonPath("picture").value(newPicture))
        .andExpect(jsonPath("_links.self").exists());

    Account newAccount = accountRepository.findById(testUser.getAccount().getId()).orElseThrow();

    Assertions.assertThat(newAccount.getBio()).isEqualTo(newBio);
    Assertions.assertThat(newAccount.getName()).isEqualTo(newName);
    Assertions.assertThat(newAccount.getPicture()).isEqualTo(newPicture);
  }
}