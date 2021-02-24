package com.nadev.naebook.account;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.nadev.naebook.account.auth.LoginUser;
import com.nadev.naebook.domain.Account;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AccountController {

  private final AccountRepository accountRepository;
  private final AccountService accountService;

  @GetMapping("/account/{id}")
  public ResponseEntity accountById(@PathVariable Long id) {
    return findAccountById(id);
  }

  @GetMapping("/account/me")
  public ResponseEntity accountMe(@LoginUser Account account) {
    return findAccountById(account.getId());
  }

  @PutMapping("/account")
  public ResponseEntity updateAccount(
      @LoginUser Account account,
      @RequestBody @Valid AccountRequestDto requestDto,
      BindingResult errors) {
    if (errors.hasErrors()) {
      return ResponseEntity.badRequest().body(errors);
    }
    Account newAccount = accountService.updateAccount(requestDto, account.getId());
    return ResponseEntity.ok(new AccountModel(newAccount));
  }

  private ResponseEntity<?> findAccountById(Long id) {
    Optional<Account> foundAccount = accountRepository.findById(id);
    if (foundAccount.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    Account account = foundAccount.get();
    AccountModel model = new AccountModel(account);
    model.add(linkTo(methodOn(AccountController.class).accountById(id)).withRel("update-account"));
    return ResponseEntity.ok(model);
  }
}
