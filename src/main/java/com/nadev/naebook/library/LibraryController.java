package com.nadev.naebook.library;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nadev.naebook.account.AccountRepository;
import com.nadev.naebook.account.AccountService;
import com.nadev.naebook.account.auth.LoginUser;
import com.nadev.naebook.common.ResponseDto;
import com.nadev.naebook.domain.Account;
import com.nadev.naebook.domain.library.AccountBook;
import com.nadev.naebook.domain.library.BookAccess;
import com.nadev.naebook.domain.library.BookStatus;
import com.nadev.naebook.exception.ForbiddenException;
import com.nadev.naebook.exception.NotFoundException;
import com.nadev.naebook.exception.NotReviewedException;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/library")
@RequiredArgsConstructor
public class LibraryController {

  private final AccountBookRepository accountBookRepository;
  private final AccountRepository accountRepository;
  private final LibraryService libraryService;
  private final ObjectMapper objectMapper;

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

  @GetMapping("/{accountId}/books")
  public ResponseEntity findBooksByAccount(
      @PathVariable Long accountId, @LoginUser Account account) {
    if (!existsAccount(accountId)) {
      return ResponseEntity.notFound().build();
    }
    List<AccountBook> accountBooks = accountBookRepository.findAllByAccount(accountId);
    List<AccountBookModel> models =
        accountBooks.stream()
            .filter(book -> accessible(account, book))
            .map(AccountBookModel::new)
            .collect(Collectors.toList());
    return ResponseEntity.ok(new ResponseDto<>(models));
  }

  private boolean accessible(Account account, AccountBook findBook) {
    return findBook.getAccess() == BookAccess.PUBLIC ||
        findBook.getAccount().getId() == account.getId();
  }

  @PutMapping("/books/{bookId}/access")
  public ResponseEntity changeBooksAccess(
      @PathVariable Long bookId, @LoginUser Account account, @RequestBody String bookAccess) {
    BookAccess access = BookAccess.valueOf(bookAccess);
    try {
      AccountBook accountBook =
          libraryService.changeBookAccess(account.getId(), bookId, access);
      return ResponseEntity.ok(new AccountBookModel(accountBook));
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (ForbiddenException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
  }

  @PutMapping("/books/{bookId}/status")
  public ResponseEntity changeBookStatus(
      @PathVariable Long bookId, @LoginUser Account account, @RequestBody String bookStatus) {
    BookStatus status = BookStatus.valueOf(bookStatus);
    try {
      AccountBook accountBook =
          libraryService.changeBookStatus(account.getId(), bookId, status);
      return ResponseEntity.ok(new AccountBookModel(accountBook));
    } catch (NotReviewedException e) {
      return ResponseEntity.badRequest().body("not reviewed yet");
    } catch (NotFoundException e) {
      return ResponseEntity.notFound().build();
    } catch (ForbiddenException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
  }

  private boolean existsAccount(Long accountId) {
    return accountRepository.findById(accountId).isPresent();
  }
}
