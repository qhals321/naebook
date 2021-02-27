package com.nadev.naebook.library;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nadev.naebook.domain.library.AccountBook;
import com.nadev.naebook.domain.library.BookAccess;
import com.nadev.naebook.domain.library.BookStatus;
import com.nadev.naebook.library.dto.AccessRequestDto;
import com.nadev.naebook.library.dto.ReviewRequestDto;
import com.nadev.naebook.library.dto.StatusRequestDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.EntityModel;

@Getter
@Setter
public class AccountBookModel extends EntityModel<AccountBook> {

  private Long id;
  private Long accountId;
  private String isbn;
  private BookAccess access;
  private BookStatus status;
  private String review;
  private String category;
  private Float score;
  private boolean reviewed;


  public AccountBookModel(AccountBook accountBook) {
    this.id = accountBook.getId();
    this.isbn = accountBook.getIsbn();
    this.access = accountBook.getAccess();
    this.accountId = accountBook.getAccount().getId();
    this.status = accountBook.getStatus();
    this.review = accountBook.getReview();
    this.category = accountBook.getCategory();
    this.score = accountBook.getScore();
    this.reviewed = accountBook.isReviewed();
    add(linkTo(methodOn(LibraryController.class)
        .findBook(id, accountBook.getAccount()))
        .withSelfRel());
    add(linkTo(methodOn(LibraryController.class)
        .changeBooksAccess(id, accountBook.getAccount(), new AccessRequestDto(access)))
        .withRel("accountBook-changeAccess"));
    add(linkTo(methodOn(LibraryController.class)
        .changeBookStatus(id, accountBook.getAccount(), new StatusRequestDto(status)))
        .withRel("accountBook-changeStatus"));
    add(linkTo(methodOn(LibraryController.class)
        .writeReview(id, accountBook.getAccount(), new ReviewRequestDto(), null))
        .withRel("accountBook-review")
    );
  }
}
