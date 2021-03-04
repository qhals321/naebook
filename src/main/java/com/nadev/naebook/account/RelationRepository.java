package com.nadev.naebook.account;

import com.nadev.naebook.domain.Relation;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RelationRepository extends JpaRepository<Relation, Long> {

    @Query("select re from Relation re where re.follower.id = :followerId and re.followee.id = :followeeId")
    Optional<Relation> findByBothId(@Param("followerId") Long followerId, @Param("followeeId") Long followeeId);
}
