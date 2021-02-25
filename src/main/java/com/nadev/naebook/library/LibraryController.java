package com.nadev.naebook.library;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.nadev.naebook.account.AccountRepository;
import com.nadev.naebook.account.auth.LoginUser;
import com.nadev.naebook.common.ResponseDto;
import com.nadev.naebook.domain.Account;
import com.nadev.naebook.domain.library.AccountBook;
import com.nadev.naebook.domain.library.BookAccess;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/library")
@RequiredArgsConstructor
public class LibraryController {

  private final AccountBookRepository accountBookRepository;
  private final AccountRepository accountRepository;

  @PostMapping("/books")
  public ResponseEntity booking(@LoginUser Account account, @RequestBody String isbn) {
    boolean present = accountBookRepository.findWithAccountIsbn(account.getId(), isbn).isPresent();
    if (present) {
      return ResponseEntity.badRequest().body("already exists");
    }
    AccountBook accountBook = AccountBook.of(isbn, account);
    AccountBook saved = accountBookRepository.save(accountBook);
    URI uri = linkTo(methodOn(LibraryController.class)
        .findBook(saved.getId(), account)).withSelfRel()
        .toUri();
    return ResponseEntity.created(uri).body(new AccountBookModel(saved));
  }

  @GetMapping("/books/{bookId}")
  public ResponseEntity findBook(@PathVariable Long bookId, @LoginUser Account account) {
    Optional<AccountBook> accountBook = accountBookRepository.findById(bookId);
    if (accountBook.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    AccountBook findBook = accountBook.get();
    if (!accessible(account, findBook)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    return ResponseEntity.ok(new AccountBookModel(findBook));
  }

  private boolean accessible(Account account, AccountBook findBook) {
    return findBook.getAccess() == BookAccess.PUBLIC ||
        findBook.getAccount().getId() == account.getId();
  }

  @GetMapping("/{accountId}/books")
  public ResponseEntity findBooksByAccount(
      @PathVariable Long accountId, @LoginUser Account account) {
    Optional<Account> findAccount = accountRepository.findById(accountId);
    if (findAccount.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    List<AccountBook> accountBooks = accountBookRepository.findAllByAccount(accountId);
    List<AccountBookModel> models;
    if (!accountId.equals(account.getId())) {
      models = accountBooks.stream()
          .filter(book -> book.getAccess() != BookAccess.PRIVATE)
          .map(AccountBookModel::new)
          .collect(Collectors.toList());
    } else {
      models = accountBooks.stream()
          .map(AccountBookModel::new)
          .collect(Collectors.toList());
    }
    return ResponseEntity.ok(new ResponseDto<>(models));
  }

}
