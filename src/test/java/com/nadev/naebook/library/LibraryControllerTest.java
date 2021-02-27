package com.nadev.naebook.library;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nadev.naebook.account.AccountRepository;
import com.nadev.naebook.account.AccountService;
import com.nadev.naebook.account.TestUser;
import com.nadev.naebook.account.auth.Role;
import com.nadev.naebook.domain.Account;
import com.nadev.naebook.domain.library.AccountBook;
import com.nadev.naebook.domain.library.BookAccess;
import com.nadev.naebook.domain.library.BookStatus;
import com.nadev.naebook.library.dto.AccessRequestDto;
import com.nadev.naebook.library.dto.BookRequestDto;
import com.nadev.naebook.library.dto.ReviewRequestDto;
import com.nadev.naebook.library.dto.StatusRequestDto;
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
import org.springframework.test.web.servlet.ResultMatcher;

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
  @Autowired
  private LibraryService libraryService;
  @Autowired
  private ObjectMapper objectMapper;

  @AfterEach
  private void afterEach() {
    testUser.deleteAll();
  }

  @Test
  @DisplayName("이미 등록되어 있는 책을 다시 등록할 때")
  void createAccountBook_duplicate() throws Exception {
    AccountBook accountBook = AccountBook.of("123", testUser.getAccount());
    accountBookRepository.save(accountBook);

    mockMvc.perform(post(BASE_URL + "/books")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        .contentType(MediaType.APPLICATION_JSON)
        .content("123")
    )
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  void createAccountBook() throws Exception {
    BookRequestDto requestDto = new BookRequestDto("123456");
    Long accountId = testUser.getAccount().getId();
    mockMvc.perform(post(BASE_URL + "/books")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(requestDto))
    )
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("access").value(BookAccess.PUBLIC.name()))
        .andExpect(jsonPath("status").value(BookStatus.BOOKING.name()))
        .andExpect(jsonPath("reviewed").value(false))
        .andExpect(jsonPath("isbn").value(requestDto.getIsbn()))
        .andExpect(jsonPath("accountId").value(accountId));

    accountBookRepository.findWithAccountIsbn(accountId, requestDto.getIsbn()).orElseThrow();
  }

  @Test
  @DisplayName("존재하지 않는 accountBook을 찾으려할 때")
  void findAccountBook_invalidId() throws Exception {
    mockMvc.perform(get(BASE_URL + "/books/-100")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
    )
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("다른 유저의 private account book에 접근할 때")
  void findAccountBook_private() throws Exception {
    Account otherUser = otherAccount();
    Account savedAccount = accountRepository.save(otherUser);
    AccountBook testBook = AccountBook.of("test", savedAccount);
    testBook.changeAccess(BookAccess.PRIVATE);
    AccountBook savedBook = accountBookRepository.save(testBook);

    mockMvc.perform(get(BASE_URL + "/books/" + savedBook.getId())
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

    mockMvc.perform(get(BASE_URL + "/books/" + savedBook.getId())
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

    mockMvc.perform(get(BASE_URL + "/books/" + savedBook.getId())
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

  @Test
  @DisplayName("없는 계정의 라이브러리 책들을 요구")
  void findAllBooksByAccount_invalidAccount() throws Exception {
    mockMvc.perform(get(BASE_URL + "/-100/books")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
    )
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("다른 계정의 라이브러리 책들 요구 시 private access로 되어 있는 것은 필터링")
  void findAllBooksByAccount_withoutPrivate() throws Exception {
    Account otherUser = otherAccount();
    Account savedAccount = accountRepository.save(otherUser);
    AccountBook testBook = AccountBook.of("privateTest", savedAccount);
    AccountBook test1 = AccountBook.of("test1", savedAccount);
    AccountBook test2 = AccountBook.of("test2", savedAccount);
    AccountBook test3 = AccountBook.of("test3", savedAccount);
    AccountBook test4 = AccountBook.of("test4", savedAccount);
    testBook.changeAccess(BookAccess.PRIVATE);
    accountBookRepository.save(testBook);
    accountBookRepository.save(test1);
    accountBookRepository.save(test2);
    accountBookRepository.save(test3);
    accountBookRepository.save(test4);

    mockMvc.perform(get(BASE_URL + "/" + savedAccount.getId() + "/books")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data[*].isbn")
            .value(Matchers.containsInAnyOrder(test1.getIsbn(), test2.getIsbn(),
                test3.getIsbn(), test4.getIsbn())));
  }

  @Test
  void findAllBooksByAccount() throws Exception {
    Account account = testUser.getAccount();
    AccountBook testBook = AccountBook.of("privateTest", account);
    AccountBook test1 = AccountBook.of("test1", account);
    AccountBook test2 = AccountBook.of("test2", account);
    AccountBook test3 = AccountBook.of("test3", account);
    AccountBook test4 = AccountBook.of("test4", account);
    testBook.changeAccess(BookAccess.PRIVATE);
    accountBookRepository.save(testBook);
    accountBookRepository.save(test1);
    accountBookRepository.save(test2);
    accountBookRepository.save(test3);
    accountBookRepository.save(test4);

    mockMvc.perform(get(BASE_URL + "/" + account.getId() + "/books")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data[*].isbn")
            .value(Matchers.containsInAnyOrder(test1.getIsbn(), test2.getIsbn(),
                test3.getIsbn(), test4.getIsbn(), testBook.getIsbn())));
  }

  @Test
  @DisplayName("Book Access 변환 시 Book id 가 잘못될 경우")
  void changeAccess_invalidId() throws Exception {
    mockMvc.perform(put(BASE_URL + "/books/-100/access")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new AccessRequestDto(BookAccess.PRIVATE)))
    )
        .andDo(print())
        .andExpect(status().isNotFound());

  }

  @Test
  @DisplayName("Book Access 변환 시 남의 book을 변경")
  void changeAccess_forbidden() throws Exception {
    Account otherUser = otherAccount();
    Account savedAccount = accountRepository.save(otherUser);
    AccountBook testBook = AccountBook.of("privateTest", savedAccount);
    AccountBook saved = accountBookRepository.save(testBook);

    mockMvc.perform(put(BASE_URL + "/books/" + saved.getId() + "/access")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new AccessRequestDto(BookAccess.PRIVATE)))
    )
        .andDo(print())
        .andExpect(status().isForbidden());
  }

  @Test
  void changeAccess() throws Exception {
    AccountBook testBook = AccountBook.of("privateTest", testUser.getAccount());
    AccountBook saved = accountBookRepository.save(testBook);

    mockMvc.perform(put(BASE_URL+ "/books/" + saved.getId() + "/access")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new AccessRequestDto(BookAccess.PRIVATE)))
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("isbn").value(saved.getIsbn()))
        .andExpect(jsonPath("access").value(BookAccess.PRIVATE.name()));

    AccountBook accountBook = accountBookRepository.findById(saved.getId()).orElseThrow();
    Assertions.assertThat(accountBook.getAccess()).isEqualTo(BookAccess.PRIVATE);
  }

  @Test
  @DisplayName("없는 book의 status를 변경 시")
  void changeStatus_invalidBook() throws Exception {
    mockMvc.perform(put(BASE_URL+"/books/-100/status")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new StatusRequestDto(BookStatus.READING)))
    )
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("다른 계정의 status를 변경 시")
  void changeStatus_forbidden() throws Exception {
    Account otherUser = otherAccount();
    Account savedAccount = accountRepository.save(otherUser);
    AccountBook testBook = AccountBook.of("privateTest", savedAccount);
    AccountBook saved = accountBookRepository.save(testBook);

    mockMvc.perform(put(BASE_URL + "/books/" + saved.getId() + "/status")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new StatusRequestDto(BookStatus.READING)))
    )
        .andDo(print())
        .andExpect(status().isForbidden());
  }

  @Test
  @DisplayName("리뷰를 하지 않고 COMPLETE로 status를 변경시")
  void changeStatus_notReviewed() throws Exception {
    AccountBook testBook = AccountBook.of("privateTest", testUser.getAccount());
    AccountBook saved = accountBookRepository.save(testBook);

    mockMvc.perform(put(BASE_URL+ "/books/" + saved.getId() + "/status")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new StatusRequestDto(BookStatus.COMPLETE)))
    )
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }

  @Test
  @DisplayName("북킹에서 리딩으로 status 변경시")
  void changeStatus_READING() throws Exception {
    AccountBook testBook = AccountBook.of("privateTest", testUser.getAccount());
    AccountBook saved = accountBookRepository.save(testBook);

    mockMvc.perform(put(BASE_URL+ "/books/" + saved.getId() + "/status")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new StatusRequestDto(BookStatus.READING)))
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("isbn").value(saved.getIsbn()))
        .andExpect(jsonPath("status").value(BookStatus.READING.name()));

    AccountBook accountBook = accountBookRepository.findById(saved.getId()).orElseThrow();
    Assertions.assertThat(accountBook.getStatus()).isEqualTo(BookStatus.READING);
  }

  @Test
  @DisplayName("COMPLETE으로 status 변경시")
  void changeStatus_COMPLETE() throws Exception {
    Account currentAccount = testUser.getAccount();
    AccountBook testBook = AccountBook.of("privateTest", currentAccount);
    AccountBook saved = accountBookRepository.save(testBook);
    libraryService.review(currentAccount.getId(), saved.getId(), 2F, "good");

    mockMvc.perform(put(BASE_URL+ "/books/" + saved.getId() + "/status")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new StatusRequestDto(BookStatus.COMPLETE)))
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("isbn").value(saved.getIsbn()))
        .andExpect(jsonPath("status").value(BookStatus.COMPLETE.name()));

    AccountBook accountBook = accountBookRepository.findById(saved.getId()).orElseThrow();
    Assertions.assertThat(accountBook.getStatus()).isEqualTo(BookStatus.COMPLETE);
  }

  @Test
  @DisplayName("남의 북에 리뷰를 남길 시")
  void bookReview_forbidden() throws Exception {
    Account account = otherAccount();
    Account saved = accountRepository.save(account);
    AccountBook accountBook = AccountBook.of("1234", saved);
    AccountBook savedBook = accountBookRepository.save(accountBook);

    mockMvc.perform(put(BASE_URL+"/books/"+savedBook.getId()+"/review")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new ReviewRequestDto(3F, "good")))
    )
        .andDo(print())
        .andExpect(status().isForbidden());
  }

  @Test
  @DisplayName("없는 북 리뷰를 남길 시")
  void bookReview_notFound() throws Exception {
    mockMvc.perform(put(BASE_URL+"/books/-100/review")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new ReviewRequestDto(3F,"good")))
    )
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  @DisplayName("점수를 안 남길 시")
  void bookReview_emptyScore() throws Exception {

    AccountBook accountBook = AccountBook.of("123", testUser.getAccount());
    AccountBook saved = accountBookRepository.save(accountBook);
    mockMvc.perform(put(BASE_URL+"/books/" + saved.getId() + "/review")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new ReviewRequestDto(null, "good")))
    )
        .andDo(print())
        .andExpect(status().is4xxClientError());
  }

  @Test
  @DisplayName("점수만 남길 시 리뷰 성공")
  void bookReview_success() throws Exception {
    AccountBook accountBook = AccountBook.of("123", testUser.getAccount());
    AccountBook saved = accountBookRepository.save(accountBook);
    mockMvc.perform(put(BASE_URL+"/books/" + saved.getId() + "/review")
        .with(oauth2Login().oauth2User(testUser.getAuth2User()))
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(new ReviewRequestDto(2F, null)))
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("score").value(2F))
        .andExpect(jsonPath("review").isEmpty());

    AccountBook book = accountBookRepository.findById(saved.getId()).orElseThrow();
    Assertions.assertThat(book.isReviewed()).isTrue();
  }

  private Account otherAccount() {
    return Account.builder()
        .role(Role.USER)
        .name("otherUser")
        .email("test@test.com")
        .build();
  }
}