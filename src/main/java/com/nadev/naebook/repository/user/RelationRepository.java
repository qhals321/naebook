package com.nadev.naebook.repository.user;

import com.nadev.naebook.domain.user.Relation;
import com.nadev.naebook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RelationRepository extends JpaRepository<Relation, Long> {

  @Query("select count(r) from Relation r where r.follower = :user")
  Long countFollower(User user);

  @Query("select count(r) from Relation r where r.followee = :user")
  Long countFollowee(User user);
}
