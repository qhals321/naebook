package com.nadev.naebook.library;

import static org.junit.jupiter.api.Assertions.*;

import com.nadev.naebook.account.TestUser;
import com.nadev.naebook.domain.library.AccountBook;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class AccountBookRepositoryTest {

  @Autowired
  private AccountBookRepository accountBookRepository;
  @Autowired
  private TestUser testUser;

  @Test
  void testAccountBookRepository() {
    String isbn = "123";
    AccountBook accountBook = AccountBook.of(isbn, testUser.getAccount());
    accountBookRepository.save(accountBook);
    AccountBook find = accountBookRepository
        .findWithAccountIsbn(testUser.getAccount().getId(), isbn).orElseThrow();

    Assertions.assertThat(find.getIsbn()).isEqualTo(isbn);
  }
}