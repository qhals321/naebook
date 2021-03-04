package com.nadev.naebook.account;

import com.nadev.naebook.account.dto.AccountRequestDto;
import com.nadev.naebook.domain.Account;
import com.nadev.naebook.domain.AccountTag;
import com.nadev.naebook.domain.Relation;
import com.nadev.naebook.domain.Tag;
import com.nadev.naebook.exception.AlreadyExistsException;
import com.nadev.naebook.exception.NotFoundException;
import com.nadev.naebook.library.TagRepository;
import java.util.Optional;
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
  private final RelationRepository relationRepository;

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

  @Transactional
  public Relation followAccount(Long followerId, Long followeeId) {
    Account follower = accountRepository.findById(followerId).orElseThrow(NotFoundException::new);
    Account followee = accountRepository.findById(followeeId).orElseThrow(NotFoundException::new);

    Optional<Relation> findRelation = relationRepository.findByBothId(followerId, followeeId);

    if(findRelation.isPresent()) {
      throw new AlreadyExistsException();
    }

    followee.upInterest();
    return relationRepository.save(Relation.of(follower, followee));
  }

  @Transactional
  public void unfollowAccount(Long followerId, Long followeeId) {
    Account follower = accountRepository.findById(followerId).orElseThrow(NotFoundException::new);
    Account followee = accountRepository.findById(followeeId).orElseThrow(NotFoundException::new);

    Optional<Relation> findRelation = relationRepository.findByBothId(followerId, followeeId);

    relationRepository.delete(findRelation.orElseThrow(NotFoundException::new));
    followee.downInterest();
  }
}
