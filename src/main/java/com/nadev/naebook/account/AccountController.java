package com.nadev.naebook.account;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {

  private final AccountRepository accountRepository;

  @GetMapping("/account/{id}")
  public ResponseEntity account(@PathVariable Long id) {
    Optional<Account> foundAccount = accountRepository.findById(id);
    if (foundAccount.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    Account account = foundAccount.get();
    return ResponseEntity.ok(new AccountModel(account));
  }
}
