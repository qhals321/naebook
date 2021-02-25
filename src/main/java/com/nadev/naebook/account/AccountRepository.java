package com.nadev.naebook.account;

import com.nadev.naebook.domain.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

  Optional<Account> findByEmail(String email);
}
