package com.nadev.naebook.library;

import com.nadev.naebook.domain.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

  Optional<Tag> findByTitle(String title);
}
