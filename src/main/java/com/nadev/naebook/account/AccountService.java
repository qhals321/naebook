package com.nadev.naebook.account;

import com.nadev.naebook.domain.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

  private final AccountRepository accountRepository;

  @Transactional
  public Account updateAccount(AccountRequestDto requestDto, Long id) {
    Account account = accountRepository.findById(id)
        .orElseThrow();

    account.updateAccount(requestDto.getName(), requestDto.getBio(), requestDto.getPicture());
    return account;
  }
}
