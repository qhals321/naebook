package com.nadev.naebook.v1.domain.library;

import com.nadev.naebook.v1.domain.user.User;
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

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UserBook {

  @GeneratedValue @Id
  @Column(name = "user_book_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  private String isbn;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private BookStatus status;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AccessStatus access;

  private String review;

  private Float score;

  public static UserBook of(String isbn, User user) {
    UserBook book = new UserBook();
    book.isbn = isbn;
    book.status = BookStatus.BOOKING;
    book.access = AccessStatus.PUBLIC;
    book.user = user;
    return book;
  }
}
