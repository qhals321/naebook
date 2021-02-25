package com.nadev.naebook.library;

import com.nadev.naebook.domain.Account;
import com.nadev.naebook.domain.library.AccountBook;
import com.nadev.naebook.domain.library.BookAccess;
import com.nadev.naebook.domain.library.BookStatus;
import com.nadev.naebook.exception.ForbiddenException;
import com.nadev.naebook.exception.NotFoundException;
import com.nadev.naebook.exception.NotReviewedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LibraryService {

  private final AccountBookRepository accountBookRepository;

  @Transactional
  public AccountBook changeBookAccess(Long accountId, Long bookId, BookAccess bookAccess) {
    AccountBook accountBook = validateAccountBook(accountId, bookId);
    accountBook.changeAccess(bookAccess);
    return accountBook;
  }

  @Transactional
  public AccountBook changeBookStatus(Long accountId, Long bookId, BookStatus bookStatus)
      throws NotReviewedException {
    AccountBook accountBook = validateAccountBook(accountId, bookId);
    accountBook.changeStatus(bookStatus);
    return accountBook;
  }

  private AccountBook validateAccountBook(Long accountId, Long bookId) {
    AccountBook accountBook =
        accountBookRepository.findById(bookId)
        .orElseThrow(NotFoundException::new);

    if(!accountId.equals(accountBook.getAccount().getId())) {
      throw new ForbiddenException();
    }

    return accountBook;
  }
}
