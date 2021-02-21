package com.nadev.naebook.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Library {

  @GeneratedValue @Id
  @Column(name = "library_id")
  private Long id;

  private String isbn;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private BookStatus status;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AccessStatus access;

  private String review;

  // 처음에는 BOOKMARK -> 관심, PUbLIC -> 공개,
  private Float score;

  public static Library of(String isbn) {
    Library library = new Library();
    library.isbn = isbn;
    library.status = BookStatus.BOOKING;
    library.access = AccessStatus.PUBLIC;
    return library;
  }


}
