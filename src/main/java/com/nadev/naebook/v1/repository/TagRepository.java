package com.nadev.naebook.v1.repository;

import com.nadev.naebook.v1.domain.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

  Optional<Tag> findByTitle(String title);
}
