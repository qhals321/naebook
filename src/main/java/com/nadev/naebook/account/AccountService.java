package com.nadev.naebook.account;

import com.nadev.naebook.account.dto.AccountRequestDto;
import com.nadev.naebook.domain.Account;
import com.nadev.naebook.domain.AccountTag;
import com.nadev.naebook.domain.Tag;
import com.nadev.naebook.library.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

  private final AccountRepository accountRepository;
  private final TagRepository tagRepository;
  private final AccountTagRepository accountTagRepository;

  @Transactional
  public Account updateAccount(AccountRequestDto requestDto, Long id) {
    Account account = accountRepository.findById(id)
        .orElseThrow();

    account.updateAccount(requestDto.getName(), requestDto.getBio(), requestDto.getPicture());
    return account;
  }

  @Transactional
  public AccountTag createAccountTag(Long accountId, String title) {
    Tag tag = tagRepository.findByTitle(title).orElseGet(() -> tagRepository.save(Tag.from(title)));
    Account account = accountRepository.findById(accountId).orElseThrow();
    AccountTag accountTag = accountTagRepository.save(AccountTag.of(account, tag));
    accountTag.increaseInterest();
    return accountTag;
  }
}
