package com.nadev.naebook.v1.api;

import com.nadev.naebook.v1.auth.LoginUser;
import com.nadev.naebook.v1.auth.dto.SessionUser;
import com.nadev.naebook.v1.domain.library.AccessStatus;
import com.nadev.naebook.v1.domain.library.BookStatus;
import com.nadev.naebook.v1.domain.library.UserBook;
import com.nadev.naebook.v1.dto.ResponseDto;
import com.nadev.naebook.v1.repository.library.UserBookRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
@CrossOrigin(value = "http://localhost:8080")
public class LibraryController {

  private final UserBookRepository userBookRepository;

  @PostMapping("/library/booking")
  public BookingResponseDto booking(@LoginUser SessionUser user, @RequestBody String isbn) {
    UserBook userBook = userBookRepository.save(UserBook.of(isbn, user.toUserEntity()));
    return new BookingResponseDto(userBook);
  }



  @Data
  static class BookingResponseDto {

    private String isbn;
    private BookStatus status;
    private AccessStatus access;
    private String review;
    private Float score;

    public BookingResponseDto(UserBook userBook) {
      this.isbn = userBook.getIsbn();
      this.status = userBook.getStatus();
      this.access = userBook.getAccess();
      this.review = userBook.getReview();
      this.score = userBook.getScore();
    }
  }
}
