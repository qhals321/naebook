package com.nadev.naebook.library;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.nadev.naebook.account.AccountRepository;
import com.nadev.naebook.account.auth.LoginUser;
import com.nadev.naebook.common.ResponseDto;
import com.nadev.naebook.domain.Account;
import com.nadev.naebook.domain.library.AccountBook;
import com.nadev.naebook.domain.library.BookAccess;
import com.nadev.naebook.domain.library.BookStatus;
import com.nadev.naebook.exception.AlreadyExistsException;
import com.nadev.naebook.exception.NotFoundException;
import com.nadev.naebook.library.dto.AccessRequestDto;
import com.nadev.naebook.library.dto.BookRequestDto;
import com.nadev.naebook.library.dto.ReviewRequestDto;
import com.nadev.naebook.library.dto.StatusRequestDto;
import com.nadev.naebook.library.model.AccountBookModel;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PostMapping("/books")
    public ResponseEntity booking(@LoginUser Account account,
        @RequestBody BookRequestDto requestDto) {
        accountBookRepository.findWithAccountIsbn(account.getId(),
            requestDto.getIsbn()).ifPresent(book -> {
            throw new AlreadyExistsException();
        });

        AccountBook accountBook = AccountBook.of(requestDto.getIsbn(), account);
        AccountBook saved = accountBookRepository.save(accountBook);

        URI uri = linkTo(methodOn(LibraryController.class)
            .findBook(saved.getId(), account)).withSelfRel()
            .toUri();

        return ResponseEntity.created(uri).body(new AccountBookModel(saved));
    }

    @GetMapping("/books/{bookId}")
    public ResponseEntity findBook(@PathVariable Long bookId, @LoginUser Account account) {
        AccountBook accountBook = accountBookRepository.findById(bookId)
            .orElseThrow(NotFoundException::new);

        if (!accessible(account, accountBook)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(new AccountBookModel(accountBook));
    }

    @GetMapping("/{accountId}/books")
    public ResponseEntity findBooksByAccount(
        @PathVariable Long accountId, @LoginUser Account account) {
        accountRepository.findById(accountId).orElseThrow(NotFoundException::new);

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
            findBook.getAccount().getId().equals(account.getId());
    }

    @PutMapping("/books/{bookId}/access")
    public ResponseEntity changeBooksAccess(
        @PathVariable Long bookId, @LoginUser Account account,
        @RequestBody AccessRequestDto requestDto) {
        BookAccess access = requestDto.getAccess();
        AccountBook accountBook =
            libraryService.changeBookAccess(account.getId(), bookId, access);
        return ResponseEntity.ok(new AccountBookModel(accountBook));
    }

    @PutMapping("/books/{bookId}/status")
    public ResponseEntity changeBookStatus(
        @PathVariable Long bookId, @LoginUser Account account,
        @RequestBody StatusRequestDto requestDto) {
        BookStatus status = requestDto.getStatus();
        AccountBook accountBook =
            libraryService.changeBookStatus(account.getId(), bookId, status);
        return ResponseEntity.ok(new AccountBookModel(accountBook));
    }

    @PutMapping("/books/{bookId}/review")
    public ResponseEntity writeReview(
        @PathVariable Long bookId,
        @LoginUser Account account,
        @RequestBody @Valid ReviewRequestDto requestDto,
        BindingResult errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        AccountBook reviewedBook = libraryService.review(
            account.getId(),
            bookId,
            requestDto.getScore(),
            requestDto.getReview()
        );

        return ResponseEntity.ok().body(new AccountBookModel(reviewedBook));
    }

    @DeleteMapping("/books/{bookId}")
    public ResponseEntity deleteBook(@LoginUser Account account, @PathVariable Long bookId) {
        libraryService.delete(account.getId(), bookId);
        return ResponseEntity.ok().build();
    }
}
