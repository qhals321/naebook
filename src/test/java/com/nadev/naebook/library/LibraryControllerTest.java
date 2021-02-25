package com.nadev.naebook.library;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nadev.naebook.account.AccountRepository;
import com.nadev.naebook.account.TestUser;
import com.nadev.naebook.account.auth.Role;
import com.nadev.naebook.domain.Account;
import com.nadev.naebook.domain.library.AccountBook;
import com.nadev.naebook.domain.library.BookAccess;
import com.nadev.naebook.domain.library.BookStatus;
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
class LibraryControllerTest {

  private static final String BASE_URL = "/api/library";

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private TestUser testUser;
  @Autowired
  private AccountBookRepository accountBookRepository;
  @Autowired
  private AccountRepository accountRepository;

  @AfterEach
  private void afterEach() {
    testUser.deleteAll();
  }

  @Test
  @DisplayName("이미 등록되어 있는 책을 다시 등록할 때")
  void createAccountBook_duplicate() throws Exception {
    AccountBook accountBook = AccountBook.of("123", testUser.getAccount());
    accountBookRepository.save(accountBook);

    mockMvc.perform(post(BASE_URL + "/book")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        .contentType(MediaType.APPLICATION_JSON)
        .content("123")
    )
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void createAccountBook() throws Exception {
    String isbn = "123456";
    Long accountId = testUser.getAccount().getId();
    mockMvc.perform(post(BASE_URL + "/book")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        .contentType(MediaType.APPLICATION_JSON)
        .content(isbn)
    )
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("access").value(BookAccess.PUBLIC.name()))
        .andExpect(jsonPath("status").value(BookStatus.BOOKING.name()))
        .andExpect(jsonPath("reviewed").value(false))
        .andExpect(jsonPath("isbn").value(isbn))
        .andExpect(jsonPath("accountId").value(accountId));

    accountBookRepository.findWithAccountIsbn(accountId, isbn).orElseThrow();
  }

  @Test
  @DisplayName("존재하지 않는 accountBook을 찾으려할 때")
  void findAccountBook_invalidId() throws Exception {
    mockMvc.perform(get(BASE_URL + "/book/-100")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
    )
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("다른 유저의 private account book에 접근할 때")
  void findAccountBook_private() throws Exception {
    Account otherUser = Account.builder()
        .role(Role.USER)
        .name("otherUser")
        .email("test@test.com")
        .build();
    Account savedAccount = accountRepository.save(otherUser);
    AccountBook testBook = AccountBook.of("test", savedAccount);
    testBook.changeAccess(BookAccess.PRIVATE);
    AccountBook savedBook = accountBookRepository.save(testBook);

    mockMvc.perform(get(BASE_URL + "/book/" + savedBook.getId())
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
    )
        .andDo(print())
        .andExpect(status().isForbidden());
  }

  @Test
  @DisplayName("내 Private account book 에 접근")
  void findAccountBook_myPrivate() throws Exception {
    AccountBook testBook = AccountBook.of("test", testUser.getAccount());
    testBook.changeAccess(BookAccess.PRIVATE);
    AccountBook savedBook = accountBookRepository.save(testBook);

    mockMvc.perform(get(BASE_URL + "/book/" + savedBook.getId())
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("access").value(BookAccess.PRIVATE.name()))
        .andExpect(jsonPath("status").value(BookStatus.BOOKING.name()))
        .andExpect(jsonPath("reviewed").value(false))
        .andExpect(jsonPath("isbn").value(savedBook.getIsbn()))
        .andExpect(jsonPath("accountId").value(testUser.getAccount().getId()))
        .andExpect(jsonPath("_links.self").exists());
  }

  @Test
  void findAccountBook() throws Exception {
    AccountBook testBook = AccountBook.of("test", testUser.getAccount());
    AccountBook savedBook = accountBookRepository.save(testBook);

    mockMvc.perform(get(BASE_URL + "/book/" + savedBook.getId())
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("access").value(BookAccess.PUBLIC.name()))
        .andExpect(jsonPath("status").value(BookStatus.BOOKING.name()))
        .andExpect(jsonPath("reviewed").value(false))
        .andExpect(jsonPath("isbn").value(savedBook.getIsbn()))
        .andExpect(jsonPath("accountId").value(testUser.getAccount().getId()))
        .andExpect(jsonPath("_links.self").exists());
  }
}