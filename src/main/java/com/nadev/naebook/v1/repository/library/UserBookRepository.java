package com.nadev.naebook.v1.repository.library;

import com.nadev.naebook.v1.domain.library.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {

}
