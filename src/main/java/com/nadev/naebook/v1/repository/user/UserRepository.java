package com.nadev.naebook.v1.repository.user;

import com.nadev.naebook.v1.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);

  @Query("select u from User u join fetch u.userTags ut join fetch ut.tag tag")
  Optional<User> findAllByEmail(String email);
}
