package com.nadev.naebook.account;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.nadev.naebook.account.auth.LoginUser;
import com.nadev.naebook.account.model.AccountModel;
import com.nadev.naebook.account.model.AccountTagModel;
import com.nadev.naebook.domain.Account;
import com.nadev.naebook.domain.AccountTag;
import java.net.URI;
import java.util.Optional;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountController {

  private final AccountRepository accountRepository;
  private final AccountService accountService;
  private final AccountTagRepository accountTagRepository;

  @GetMapping("/{id}")
  public ResponseEntity accountById(@PathVariable Long id) {
    return findAccountById(id);
  }

  @GetMapping("/me")
  public ResponseEntity accountMe(@LoginUser Account account) {
    return findAccountById(account.getId());
  }

  @PutMapping
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

  @PostMapping("/tags")
  public ResponseEntity createAccountTag(@LoginUser Account account, @RequestBody String title) {
    AccountTag accountTag = accountService.createAccountTag(account.getId(), title);
    URI uri = linkTo(methodOn(AccountController.class)
        .findAccountTag(accountTag.getId()))
        .withSelfRel().toUri();
    return ResponseEntity.created(uri).build();
  }

  @GetMapping("/tags/{tagId}")
  public ResponseEntity findAccountTag(@PathVariable Long tagId) {
    Optional<AccountTag> accountTag = accountTagRepository.findById(tagId);
    if (accountTag.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    AccountTag findAccountTag = accountTag.get();
    AccountTagModel model = new AccountTagModel(findAccountTag);
    model.add(linkTo(methodOn(AccountController.class).findAccountTag(tagId)).withRel("remove-accountTag"));
    return ResponseEntity.ok(model);
  }

  @DeleteMapping("/tags/{tagId}")
  public ResponseEntity deleteAccountTag(@LoginUser Account account, @PathVariable Long tagId) {
    Optional<AccountTag> accountTag = accountTagRepository.findById(tagId);
    if (accountTag.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    AccountTag findTag = accountTag.get();
    Long accountId = findTag.getAccount().getId();
    if (!accountId.equals(account.getId())) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    accountTagRepository.delete(findTag);
    return ResponseEntity.ok().build();
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
