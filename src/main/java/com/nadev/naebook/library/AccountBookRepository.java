package com.nadev.naebook.library;

import com.nadev.naebook.domain.library.AccountBook;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountBookRepository extends JpaRepository<AccountBook, Long> {

  @Query("select ab from AccountBook ab join fetch ab.account ac where ac.id = :accountId and ab.isbn = :isbn")
  Optional<AccountBook> findWithAccountIsbn(@Param("accountId") Long accountId, @Param("isbn") String isbn);
}
