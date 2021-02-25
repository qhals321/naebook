package com.nadev.naebook.account;

import com.nadev.naebook.domain.AccountTag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountTagRepository extends JpaRepository<AccountTag, Long> {

  @Query("select at from AccountTag at join fetch at.tag t where at.account.id = :accountId")
  List<AccountTag> findAllByAccount(@Param("accountId") Long accountId);
}
