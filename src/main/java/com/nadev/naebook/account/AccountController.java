package com.nadev.naebook.account;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.nadev.naebook.account.auth.LoginUser;
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
    AccountModel model = new AccountModel(account);
    model.add(linkTo(methodOn(AccountController.class).account(id)).withRel("update-account"));
    return ResponseEntity.ok(model);
  }
}
