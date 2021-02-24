package com.nadev.naebook.account;

import static org.junit.jupiter.api.Assertions.*;

import com.nadev.naebook.domain.AccountTag;
import java.util.List;
import java.util.stream.Collectors;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AccountTagRepositoryTest {

  @Autowired
  AccountTagRepository accountTagRepository;
  @Autowired
  TestUser testUser;

  @Test
  @DisplayName("accountTagRepository의 findAllByAccount()메서드의 페치조인이 잘 되는 지 확인")
  void testAccountTagQuery() {
    String expectedTag1 = "test-tag1";
    String expectedTag2 = "test-tag2";
    String expectedTag3 = "test-tag3";
    String expectedTag4 = "test-tag4";
    List<String> tagList = accountTagRepository.findAllByAccount(testUser.getAccount().getId())
        .stream()
        .map(AccountTag::tagTitle)
        .collect(Collectors.toList());

    Assertions.assertThat(tagList.contains(expectedTag1)).isTrue();
    Assertions.assertThat(tagList.contains(expectedTag2)).isTrue();
    Assertions.assertThat(tagList.contains(expectedTag3)).isTrue();
    Assertions.assertThat(tagList.contains(expectedTag4)).isTrue();
  }
}