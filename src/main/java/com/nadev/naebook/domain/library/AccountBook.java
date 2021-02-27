package com.nadev.naebook.domain.library;

import com.nadev.naebook.domain.Account;
import com.nadev.naebook.exception.NotReviewedException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccountBook {

  @Id @GeneratedValue
  @Column(name = "book_id")
  private Long id;

  @Column(nullable = false)
  private String isbn;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", nullable = false)
  private Account account;

  @Enumerated(EnumType.STRING)
  private BookStatus status;

  private boolean reviewed;

  private String review;

  private Float score;

  private BookAccess access;

  private String category;

  public static AccountBook of(String isbn, Account account) {
    AccountBook accountBook = new AccountBook();
    accountBook.isbn = isbn;
    accountBook.account = account;
    accountBook.status = BookStatus.BOOKING;
    accountBook.reviewed = false;
    accountBook.access = BookAccess.PUBLIC;
    return accountBook;
  }

  public void review(Float score, String review) {
    this.score = score;
    this.review = review;
    this.reviewed = true;
  }

  public void changeStatus(BookStatus bookStatus) throws NotReviewedException {
    if (bookStatus == BookStatus.COMPLETE && !reviewed) {
      throw new NotReviewedException();
    }
    this.status = bookStatus;
  }

  public void changeAccess(BookAccess bookAccess) {
    this.access = bookAccess;
  }
}
